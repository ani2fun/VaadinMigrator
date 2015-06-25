import java.util.ArrayList;
import java.util.HashMap;

public class TypeWarehouse {

    private HashMap<String, ArrayList<TypeObject>> types = new HashMap<>();


    public TypeWarehouse() {

    }

    public Boolean isNull(String typeName) {
        Boolean result = true;
        if (types.isEmpty()) {
            result = true;
        } else {
            for (String typeNm : types.keySet()) {
                if (typeNm.equals(typeName)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public void updateWarehouse(String typeName, TypeObject object) {
        for (String type : types.keySet()) {
            if (type.equals(typeName)) {
                ArrayList<TypeObject> objects = new ArrayList<>(types.get(type));
                objects.add(object);
                types.put(type, objects);

                /*for(TypeObject tpObj : objects)
                {
                    if(tpObj.getName().equals(object.getName()))
                    {
                        objects.set(objects.indexOf(tpObj),object);
                        break;
                    }
                }*/

            }
        }
    }

    public void populateWarehouse(String typeName, TypeObject object) {
        ArrayList<TypeObject> type = new ArrayList<>();
        type.add(object);
        types.put(typeName, type);

    }


    public Boolean putEndLine(int line) {
        boolean result = false;
        ArrayList<TypeObject> methods = types.get("Method");
        if (methods != null) {
            for (int i = methods.size() - 1; i >= 0; i--) {
                TypeObject method = methods.get(i);
                if ((Integer) method.getEnd() == 0) {
                    method.setEnd(line);
                    result = true;
                    return result;
                }
            }
        }
        ArrayList<TypeObject> classes = types.get("Class");
        if (classes != null) {
            for (int i = classes.size() - 1; i >= 0; i--) {
                TypeObject classobj = classes.get(i);
                if ((Integer) classobj.getEnd() == 0) {
                    classobj.setEnd(line);
                    result = true;
                    return result;
                }
            }
        }

        return result;
    }

    public TypeObject getLastActiveObject() {

        ArrayList<TypeObject> methods = types.get("Method");
        if (methods != null) {
            for (int i = methods.size() - 1; i >= 0; i--) {
                TypeObject method = methods.get(i);
                if ((Integer) method.getEnd() != null) {

                    return method;
                }
            }
        }
        ArrayList<TypeObject> classes = types.get("Class");
        if (classes != null) {
            for (int i = classes.size() - 1; i <= 0; i--) {
                TypeObject classobj = classes.get(i);
                if ((Integer) classobj.getEnd() != null) {
                    return classobj;
                }
            }
        }
        return null;
    }

    public TypeObject getType(String typeName, String objectName) {
        TypeObject obj = null;
        for (TypeObject to : types.get(typeName)) {
            if (objectName.equals(to.getName())) {
                obj = to;
                break;
            }

        }
        return obj;
    }

    public Integer findPosition(String posName, TypeObject object) {
        Integer lineNumber = null;

        if (posName.equals("start")) {
            lineNumber = object.getStart();
        }
        if (posName.equals("end")) {
            lineNumber = object.getEnd();
        }

        return lineNumber;
    }



     public ArrayList<TypeObject> getTypes(String Name) {
        return types.get(Name);
    }

    public ArrayList<TypeObject> getTypes(int Id) {
        int i = 0;
        ArrayList<TypeObject> typesList = null;
        for (String typeName : types.keySet()) {
            if (i == Id) {
                typesList = types.get(typeName);
            } else {
                i++;
            }
        }
        return typesList;
    }

}