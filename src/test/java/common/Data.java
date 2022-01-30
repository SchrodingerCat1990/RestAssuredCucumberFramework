package common;

//import com.sun.data.provider.DataProvider;

import java.util.List;
import java.util.Map;

public class Data {
    static final Map<String,Object> MAP = Utilities.loadJSONToMap(System.getProperty("user.dir")
            +"/src/test/java/common/data.json");

    /*private static Object[][] getDataProvider(){
        final String dpMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        try{
            final String dpName = Data.class.getDeclaredMethod(dpMethodName).getAnnotation(DataProvider.class).name();
            final List<?> dataList = (List<?>) ((Map<?,?>) MAP.get("dataProviders")).get(dpName);
            return Utilities.toArrayOfArrays(dataList);
        }
        catch (NoSuchMethodException | SecurityException e){
            System.out.println("NoSuchMethodException | SecurityException "+ e);
        }
    }*/
}
