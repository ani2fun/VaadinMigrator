import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InplaceFileConverter {

    final static int BUFFER_LEN = 8 * 1024;
    final RuleSet ruleSet;
    boolean atLeastOneMatchOccured = false;
    final String lineTerminator;
    HashMap<TypeObject, String> anatationLine = new HashMap<TypeObject, String>();
    HashMap<String, ArrayList<String>> types = DataTypes.getDataTypes();
    TypeWarehouse tw = new TypeWarehouse();

    //int counter = 0; //add counter for calculate amount of iteration. If amount is equal 1 - break

    InplaceFileConverter(RuleSet ruleSet) {
        this.ruleSet = ruleSet;

        lineTerminator = System.getProperty("line.separator");
        //this.pl = pl;
    }

    private byte[] readIntoByteArray(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int n = 0;
        byte[] buffer = new byte[BUFFER_LEN];
        while ((n = fis.read(buffer)) != -1) {
             //System.out.println("ba="+new String(buffer, "UTF-8"));
            baos.write(buffer, 0, n);
        }
        fis.close();
        return baos.toByteArray();
    }

    void convert(File file) throws IOException {

        byte[] originalBytes = readIntoByteArray(file);
        byte[] convertedBytes = convertIntoTempByteArray(originalBytes);
        if (atLeastOneMatchOccured()) {

            writeConvertedBytesIntoFile(file, convertedBytes);

        } else {

        }
    }

    private void writeConvertedBytesIntoFile(File file, byte[] convertedBytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(convertedBytes);
        fos.flush();
        fos.close();
    }


    public String getDataType(String line) {
        Pattern pattern;
        Matcher matcher;
        String dataType = "unknown";
        for (String type : types.keySet()) {
            for (String prtn : types.get(type)) {
                pattern = Pattern.compile(prtn);
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    if (type.equals("Class") || type.equals("Method") || type.equals("if")
                            || type.equals("else") || type.equals("try") || type.equals("catch")) {
                        dataType = type;
                    }
                }

            }
        }
        return dataType;


    }

    public byte[] convertIntoTempByteArray(byte[] input) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        Reader reader = new InputStreamReader(bais);
        BufferedReader breader = new BufferedReader(reader);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArrayList<String> fileContent = new ArrayList<>();

        while (true) {
            String line = breader.readLine();

            if (line != null) {

                String typePatern = getDataType(line);


                ifUnknown(fileContent, line, typePatern);

                fileContent.add(line);
                String[] replacement = null;
                ConversionRule conversionRule;
                Pattern pattern;
                Matcher matcher;
                Iterator<ConversionRule> conversionRuleIterator = ruleSet.iterator();
                String additionalLine = null;
                String finalReplacementText = null;

                while (conversionRuleIterator.hasNext()) {

                    conversionRule = conversionRuleIterator.next();
                    pattern = conversionRule.getPattern();
                    matcher = pattern.matcher(line);
                    String lineInitial = line;

                    if (matcher.find()) {
                        // System.out.println("matching " + text);
                        atLeastOneMatchOccured = true;
                        String replacementText = conversionRule.replace(matcher);

                        line = matcher.replaceAll(replacementText);
                        finalReplacementText = conversionRule.getComments(line);

                        if (!conversionRule.getAdditionalPattern().toString().equals("")) {


                            //System.out.println(finalReplacementText);

                            if (conversionRule.getAdditionalPattern().substring(0, 6).equals("marker")) {
                                String firsPart = conversionRule.getAdditionalPattern().substring(6).split("-")[0];
                                String secondPattern = conversionRule.getAdditionalPattern().split("-")[1];


                                String[] regExPtn={ "\\s*Window(.*)=\\s*new\\s*Window\\(" , "\\s*(.*)=\\s*new\\s*Window\\(" , "view" };

                                Pattern patrnWindow = Pattern.compile(regExPtn[0]);
                                Matcher matchWindow = patrnWindow.matcher(lineInitial);

                                Pattern patrnWindow2 = Pattern.compile(regExPtn[1]);
                                Matcher matchWindow2 = patrnWindow2.matcher(lineInitial);

                                Pattern patrnVertical = Pattern.compile(regExPtn[2]);
                                Matcher matchVertical = patrnVertical.matcher(firsPart);

                                if (matchWindow.find()) {
                                    if (matchVertical.find()) {
                                        firsPart = matchVertical.replaceAll(matchWindow.group(1));
                                        secondPattern = secondPattern + matchWindow.group(1) + ");";
                                    }
                                } else if (matchWindow2.find()) {

                                    if (matchVertical.find()) {
                                        firsPart = matchVertical.replaceAll(matchWindow2.group(1));
                                        secondPattern = secondPattern + matchWindow2.group(1) + ");";
                                    }

                                }
                                TypeObject obj = tw.getLastActiveObject();
                                anatationLine.put(obj, secondPattern);
                                finalReplacementText = finalReplacementText + "\n" + firsPart;

                            } else {
                                finalReplacementText = finalReplacementText + "\n" + conversionRule.getAdditionalPattern();
                            }

                        }


                    }

                    if (conversionRule.getAdditionalLine() != null) {
                        additionalLine = conversionRule.getAdditionalLine();
                    }


                }


                pattern = Pattern.compile("}.*");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    boolean endLine = tw.putEndLine(fileContent.size() - 1);
                }
                if (finalReplacementText == null) {

                    int sizeList = fileContent.size();
                    fileContent.set(sizeList - 1, line);
                } else if (additionalLine == null) {
                    int sizeList = fileContent.size();
                    fileContent.set(sizeList - 1, finalReplacementText);
                } else {
                    replacement = new String[]{finalReplacementText, additionalLine};
                }
            } else {
                break;
             }

        }


        for (TypeObject ptrn : anatationLine.keySet()) {
            fileContent.set(ptrn.getEnd(), anatationLine.get(ptrn) + "\n" + fileContent.get(ptrn.getEnd()));
        }
        writeReplacement(baos, fileContent);

        return baos.toByteArray();
    }

    private void ifUnknown(ArrayList<String> fileContent, String line, String typePatern) {
        if (!typePatern.equals("unknown")) {
            TypeObject type = new TypeObject();
            type.setName(line);
            type.setStart(fileContent.size());
            if (tw.isNull(typePatern)) {
                tw.populateWarehouse(typePatern, type);
            } else {
                tw.updateWarehouse(typePatern, type);
            }
        }
    }


    public boolean atLeastOneMatchOccured() {
        return atLeastOneMatchOccured;
    }

    private void writeReplacement(OutputStream os, ArrayList<String> replacement)
            throws IOException {
        for (String line : replacement) {


            String[] replacementLine = new String[]{line};
            for (int i = 0; i < replacementLine.length; i++) {
                os.write(replacementLine[i].getBytes());
                os.write(lineTerminator.getBytes());
            }

        }

    }

    private ArrayList<String> reverceVarification(ArrayList<String> replacement) {
        ArrayList<String> result = new ArrayList<>();
        if (!anatationLine.isEmpty()) {


            for (String line : replacement) {
                String element = line;
                for (TypeObject ptrn : anatationLine.keySet()) {
                    Pattern pattern = Pattern.compile(ptrn.getName());
                    Matcher mtch = pattern.matcher(line);
                    if (mtch.find()) {
                        element = anatationLine.get(ptrn) + "\n" + element;
                        anatationLine.remove(ptrn);
                        continue;
                    }


                }

                result.add(element);

            }

        } else {
            result = replacement;
        }
        return result;
    }
}
