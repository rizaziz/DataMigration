
package full_stack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class QueringOracle {
    
    static Map<Integer, String> letterMapping= new HashMap();
   
    static String english_Upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String english_Lower="abcdefghijklmnopqrstuvwxyz";
    static String russian_Upper="АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    static String russian_Lower="абвгдежзийклмнопрстуфхцчшщъыьэюя";
    
    public static String convert(String str){
        String convertedWord="";
                
        Map<String,String>  keyWords=new HashMap();
        keyWords.put("ложь", "false");
        keyWords.put("ЛОЖЬ", "false");
        keyWords.put("истина", "true");
        keyWords.put("ИСТИНА", "true");
        keyWords.put("Активный", "Active");
        keyWords.put("Пассивный", "Passive");
        keyWords.put("Активный/Пассивный", "Active/Passive");
        keyWords.put("№", "#");
        
        if(keyWords.containsKey(str)){
            convertedWord=keyWords.get(str);
            return convertedWord;
        }
        
        
        String[] englishEquivalents={"A","B","V","G","D","E","J","Z","I","Y","K","L","M","N","O",
            "P","R","S","T","U","F","H","Ts","Ch","Sh","Sh","","I","","E","Yu","Ya"};
        int i=0;
        for(String letters:englishEquivalents){
            letterMapping.put(1040+i,letters);
            letterMapping.put(1072+i, letters.toLowerCase());
            i++;
        }
                
        char[] letters=str.toCharArray();
        
        for(char letter:letters){
            
            if(letterMapping.containsKey((int)letter)){
                convertedWord+=letterMapping.get((int)letter);
            } else{
                convertedWord+=Character.toString(letter);
            }
                 
        }
        
        return convertedWord;
    }
    
    
    
   
    
    public static void main(String[] args) throws Exception{
       System.out.println(new java.math.BigDecimal("-123,254.23".replaceAll(",", "")));
}
}
