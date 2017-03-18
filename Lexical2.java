/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
/**
 *
 * @author Osama Khalid
 */
public class Lexical2 {

    public static String[] keywords = {"while","if","else","for","break","continue","void","main","do","switch","case","default"};
    public static String[] Datatypes={"int","float","String","char","double","long"};
   public static  char[] wordBreakers={' ',';',':','\n','(',')','{','}',','};
  public static   int[][] TT_ID={ {1,3,2},
                    {1,1,1},
                    {1,1,3},
                    {3,3,3}
    };              
  public static int[][] TT_integer={                //TT 
      {1,2},                                        //           +,-  |  digit 
      {3,2},                                        // 0(init)   1         2 
      {3,2},                                        // 1         3         2
      {3,3}                                         // 2(final)  3         2
  };                                                // 3(dead)   3         3
  
   public static int[][] TT_float={                //TT 
      {1,2,4},                                      //           +,-  | .  | digit 
      {5,2,4},                                      // 0(init)   1      2     4
      {5,5,3},                                      // 1         5      2     4
      {5,5,3},                                      // 2         5      5     3
      {5,3,4},                                      // 3(final)  5      5     3
      {5,5,5}                                       // 4         5      3     4
                                                    // 5(dead)   5      5     5
     };               
   public static int[][] TT_char={                 //TT
       {1,5,5,5},                                  //            ' | any char/digit | \ | escape char
       {5,2,4,5},                                  // 0(init)    1       5            5      5
       {3,5,5,5},                                  // 1          5       2            4      5  
       {5,5,5,5},                                  // 2          3       5            5      5     
       {2,5,2,2},                                  // 3(final)   5       5            5      5 
       {5,5,5,5}                                   // 4          2       5            2      2 
   };                                              // 5          5       5            5      5    
   
  public static int[][] TT_string={                //TT 
      {1,5,5,5},                                   //           "  |  \  |  escape char  |  any char/digit 
      {5,4,5,2},                                   // 0(init)   1     5         5             5
      {3,4,2,2},                                   // 1         5     4         5             2
      {5,5,5,5},                                   // 2         3     4         2             2
      {2,2,2,5},                                   // 3(final)  5     5         5             5
      {5,5,5,5}                                    // 4         2     2         2             5 
  };                                               // 5         5     5         5             5 
public static boolean keywordChecking(String s){
    for(int i=0;i<keywords.length;i++){
    if (s.equals(keywords[i]))
    { 
    return true;
    }
    }
    return false;
}

public static boolean datatypeChecking(String s){
    for(int i=0;i<Datatypes.length;i++){
    if(s.compareTo(Datatypes[i])==0)
    {  
        return true;
    }
    }
    return false;
}
public static boolean identifierChecking(String s){
        int finalState=1,state=0;
        char[] array = s.toCharArray();
        for(int i=0;i<array.length;i++){
        state=TT_ID(state,array[i]);
        }
        if(state==finalState){
        return true;
        }
        else
            return false;   
}
public static int TT_ID(int state,char ch){
    if( (ch>='A' && ch<='Z') || (ch>='a' && ch<='z') )  
    {
    return TT_ID[state][0];
    }
    if(ch>='0' && ch<='9'){
    return TT_ID[state][1];
    }
    else if(ch=='_'){
    return TT_ID[state][2];
    }   
       return 3;
}
public static char digitConstChecking(String s){
        char[] array = s.toCharArray();
        char returnval;
        
        for(int i=0;i<array.length;i++){
        if(array[i]=='.'){
        if( floatChecking(s))
        { returnval='f';
        return returnval;
        }
        
        }
        
        }
        
        if(intChecking(s)){
         returnval='i';
        return returnval;
        }
        return 'n';    
}
public static boolean floatChecking(String c){
      int finalState=3,state=0;
        char[] array = c.toCharArray();
        for(int i=0;i<array.length;i++){
            state=TT_float(state,array[i]);
        }
        if(state==finalState){
        return true;
        }
        else
            return false;  
   
}
public static boolean intChecking(String c){
      int finalState=2,state=0;
        char[] array = c.toCharArray();
        for(int i=0;i<array.length;i++){
        state=TT_int(state,array[i]);
        }
     
        if(state==finalState){
        return true;
        }
        else
            return false;  
    
}   
public static int TT_float(int state,char ch){
     if(ch>=48 && ch<=57){
       
    return TT_float[state][2];
    }
    else if(ch>='.'){
      
    return TT_float[state][1];
    }
     else if( ch>='+' || ch<='-' )  
    { 
    return TT_float[state][0];
    }
       return 5;
}
public static int TT_int(int state,char ch){
    
    if(ch>=48 && ch<=57 && ch!='.'){
       
        return TT_integer[state][1];
    }   
    else if( ch>='+' && ch<='-')
    {   if(ch!='.'){ 
               
    return TT_integer[state][0];  }
    }
    
       return 3;
}
public static boolean charChecking(String s){
           int finalState=3,state=0;
        char[] array = s.toCharArray();
        for(int i=0;i<array.length;i++){
           
            state=TT_char(state,array[i]);}
      
        if(state==finalState){
            return true;
        }
        else
            return false;  
}
public static int TT_char(int state, char ch){
        if(ch=='\''){
        return TT_char[state][0];
        }
        if(ch=='\\'){ 
        return TT_char[state][2];
        }
        if(state==4){
        if(ch=='t' || ch=='b' || ch=='n' || ch=='r' || ch=='f' || ch=='\'' || ch=='\"' || ch=='\\'){
            return TT_char[state][3];   
        }   
        }
        else { 
            return TT_char[state][1];  }
        return 5;
}
public static boolean stringChecking(String s){
           int finalState=3,state=0;
        char[] array = s.toCharArray();
        for(int i=0;i<array.length;i++){
            state=TT_string(state,array[i]);
        }
        if(state==finalState){
            return true;
        }
        else
            return false;  
}
public static int TT_string(int state, char ch){
        if(ch=='\"'){
        return TT_string[state][0];
        }
        if(ch=='\\'){ 
        return TT_string[state][1];
        }
        if(state==4){
        if(ch=='t' || ch=='b' || ch=='n' || ch=='r' || ch=='f' || ch=='\'' || ch=='\"' || ch=='\\'){
            return TT_string[state][2];   
        }   
        }
        else { 
            return TT_string[state][3];  }
        return 5;
}
public static boolean checkWordbreaker(char ch){
for(int i=0;i<wordBreakers.length;i++){
    if(ch==wordBreakers[i]){
    return true;
    }    
}
return false;
}
    
    public static void main(String[] args) throws FileNotFoundException {
      Scanner s= new Scanner(new FileReader("code1.txt"));
        String a = "",temp="",b="";
        int lineCounter=0,flagString=0,flagComment=0,dotCounter=0,flagChar=0,charCount=0,charLimit=2;
        char[] array;
    while(s.hasNextLine()){
        ++lineCounter;
           a=s.nextLine();
           b=a.trim();
    array=b.toCharArray();
    for(int i=0;i<array.length;i++){
        
    if(checkWordbreaker(array[i]) && flagString!=1 && flagChar!=1 && flagComment!=1){
    if(keywordChecking(temp)){
        System.out.println("("+temp+",_,"+lineCounter+")");
        temp="";
    }
    else if(datatypeChecking(temp)){
         System.out.println("(DT,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(identifierChecking(temp)){
         System.out.println("(ID,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='f'){
         System.out.println("(float_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='i'){
         System.out.println("(int_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(charChecking(temp)){
         System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(stringChecking(temp)){
         System.out.println("(String_const,"+temp+","+lineCounter+")");
          temp="";
    }
    else { if(temp!="")
        System.out.println("InvalidB token="+temp+" at line"+lineCounter);
        temp="";
    }
    if(array[i]!=' ')
    temp=temp+array[i];
    if(checkWordbreaker(array[i]) && temp!=""){
        System.out.println("("+temp+",_,"+lineCounter+")"); 
        temp="";
    }
    continue;
    }
    else if(array[i]=='.' && flagString!=1 && flagChar!=1 && flagComment!=1){
         if(i-1>=0){
         if(array[i-1]<48 || array[i-1]>57){
             
         if(keywordChecking(temp)){
        System.out.println("("+temp+",_,"+lineCounter+")");
        temp="";
    }
    else if(datatypeChecking(temp)){
         System.out.println("(DT,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(identifierChecking(temp)){
         System.out.println("(ID,"+temp+","+lineCounter+")");
         temp="";
     }
     else { if(temp!="")
        System.out.println("InvalidDot token="+temp+" at line"+lineCounter);
        temp="";
    }
       if(i+1<array.length){
       if(array[i+1]<48 || array[i+1]>57)
       {
       System.out.println("("+array[i]+",_,"+lineCounter+")"); 
            continue; 
       }
       else {
       dotCounter++;
       temp=temp+array[i];
       continue;
       }
           }  
               
         }
         }
        ++dotCounter;
        if(dotCounter==1){
         
        temp=temp+array[i];
        continue;
        }
        else if(dotCounter==2){
        dotCounter=0;
         if(digitConstChecking(temp)=='f'){
         System.out.println("(float_const,"+temp+","+lineCounter+")");
         temp="";
     }
        else if(digitConstChecking(temp)=='i'){
         System.out.println("(int_const,"+temp+","+lineCounter+")");
         temp="";
     }
        else {
         System.out.println("Invalido token"+temp+"at line"+lineCounter);
         temp="";
        }
         if(i+1<array.length){
         if(array[i+1]>=48 && array[i+1]<=58){
         dotCounter++;
         temp=temp+array[i];
         continue;
         }
         }
            System.out.println("("+array[i]+",_,"+lineCounter+")"); 
            continue;
        }
    }
    if(array[i]=='*' && flagComment==1){
    if(i+1<array.length){
    if(array[i+1]=='/'){
    flagComment=0;
    i=i+1;
    continue;
    }
    }
    }
    //Operators Checking
    else if((array[i]=='<' || array[i]=='>' || array[i]=='!' || array[i]=='+' || array[i]=='-' || array[i]=='/' || array[i]=='%' || array[i]=='*' || array[i]=='&' || array[i]=='|' || array[i]=='=') && flagString!=1 && flagChar!=1 && flagComment!=1)
    {                                  
    if(keywordChecking(temp)){
        System.out.println("("+temp+",_,"+lineCounter+")");
        temp="";
    }
    else if(datatypeChecking(temp)){
         System.out.println("(DT,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(identifierChecking(temp)){
         System.out.println("(ID,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='f'){
         System.out.println("(float_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='i'){
         System.out.println("(int_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(charChecking(temp)){
         System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(stringChecking(temp)){
         System.out.println("(String_const,"+temp+","+lineCounter+")");
          temp="";
    }
    else if(!temp.equals("+") && !temp.equals("&") && !temp.equals("|")  && !temp.equals("-") && !temp.equals("<") && !temp.equals(">") && !temp.equals("*") && !temp.equals("/") && !temp.equals("%") && !temp.equals("=") && !temp.equals("!") && temp!="")  {
        System.out.println("Invalid token="+temp+"at line"+lineCounter);
        temp="";
    }
    if(temp!=""){
    temp=temp+array[i];
    }
    if(temp.equals("++") || temp.equals("--")){
        System.out.println("(Inc_Dec,"+temp+","+lineCounter+")");
        temp="";
        continue;
    }
    else if(temp.equals("&&") || temp.equals("||")){
        System.out.println("(AND_OR,"+temp+","+lineCounter+")");
        temp="";
         continue;
    }
    else if(temp.equals("<") || temp.equals("<=") || temp.equals(">") || temp.equals(">=") || temp.equals("!=") || temp.equals("==")){
        System.out.println("(Relational Operator,"+temp+","+lineCounter+")");
        temp="";
         continue;
    }
    else if(temp.equals("+=") || temp.equals("-=") || temp.equals("*=") || temp.equals("/=") || temp.equals("%=")){
        System.out.println("(Assign_Op2,"+temp+","+lineCounter+")");
        temp="";
         continue;
    }
    if(array[i]=='!'){
    if(i+1<array.length){
    if(array[i+1]!='='){
         System.out.println("Invalid token="+array[i]+" at line"+lineCounter);
         continue;
    }
    }
    }
    if(array[i]=='='){
    if(i+1>=array.length){
         System.out.println("(Assign_Op1,"+array[i]+","+lineCounter+")");
         continue;
     }
    else if(i+1<array.length){
     if(array[i+1]!='='){
         System.out.println("(Assign_Op1,"+array[i]+","+lineCounter+")");
         continue;
     }
     }
    
    }
     if(array[i]=='<' || array[i]=='>'){
     if(i+1>=array.length){
         System.out.println("(Relational Operator,"+array[i]+","+lineCounter+")");
         continue;
     }
     else if(i+1<array.length){
     if(array[i+1]!='='){
         System.out.println("(Relational Operator,"+array[i]+","+lineCounter+")");
         continue;
     }
     }
     }
    if(array[i]=='&' || array[i]=='|'){
    if(i+1>=array.length){
        System.out.println("Invalid token="+array[i]+" at line"+lineCounter);   
        continue;
    }
   
    else if(i+1<array.length){
        if(array[i]=='&'){
        if(array[i+1]!='&'){
        System.out.println("Invalid token="+array[i]+" at line"+lineCounter);
        continue;
        }
        }
        else if(array[i]=='|'){
        if(array[i+1]!='|') {
        System.out.println("Invalid token="+array[i]+" at line"+lineCounter);
        continue;
        }
        }
        }
    }
    if(array[i]=='+' || array[i]=='-'){
    if(i+1>=array.length){
        System.out.println("(Add_Sub,"+array[i]+","+lineCounter+")");
        continue;
    }
    else if(i+1<array.length){
    if(array[i]=='+'){
    if(array[i+1]!='+' && array[i+1]!='='){
        System.out.println("(Add_Sub,"+array[i]+","+lineCounter+")");
        continue;
    }
    }
    else if(array[i]=='-'){    
    if(array[i+1]!='-' && array[i+1]!='='){
        System.out.println("(Add_Sub,"+array[i]+","+lineCounter+")");
        continue;
    }
    }
    }
    }
    
    if(array[i]=='*' || array[i]=='/' || array[i]=='%'){
    if(i+1>=array.length){
       if(array[i]=='*') 
       {  System.out.println("(Multiply,"+array[i]+","+lineCounter+")");  
          continue;  
       }
       else
       {   System.out.println("(Div_Mod,"+array[i]+","+lineCounter+")");  
           continue; 
       }  
     }
     else if(i+1<array.length){
    if(array[i]=='/' || array[i]=='%'){
    if(array[i]=='/'){
    if(array[i+1]=='/')
    {   break;   }
    else if(array[i+1]=='*'){
    flagComment=1;    
    i=i+1;
    continue;
    }
    }    
    if(array[i+1]!='='){
        System.out.println("(Div_Mod,"+array[i]+","+lineCounter+")");
        continue;
    }
    }
    else if(array[i]=='*'){    
    if(array[i+1]!='='){
        System.out.println("(Multiply,"+array[i]+","+lineCounter+")");
        continue;
    }
    }
    }
    }
    temp=temp+array[i];
    continue;
    }
    else if(array[i]=='\'' && flagString!=1 && flagComment!=1){
    if(temp!="" &&  temp.charAt(temp.length()-1)=='\\'){
    temp=temp+array[i];
    continue;
    }
    if(flagChar==0){
    charCount=0;    
    flagChar=1;
    if(keywordChecking(temp)){
        System.out.println("("+temp+",_,"+lineCounter+")");
        temp="";
    }
    else if(datatypeChecking(temp)){
         System.out.println("(DT,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(identifierChecking(temp)){
         System.out.println("(ID,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='f'){
         System.out.println("(float_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='i'){
         System.out.println("(int_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(charChecking(temp)){
         System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(stringChecking(temp)){
         System.out.println("(String_const,"+temp+","+lineCounter+")");
         temp="";
       //  continue;
    }
    else  { if(temp!="" && temp!=" ")                         //print invalid if 
         System.out.println("InvalidChar token="+temp+ " at line number"+lineCounter);
         temp="";
    }
    }
    else if(flagChar==1){
    charCount++;    
    flagChar=0;
    }
    temp=temp+array[i];
        
    if(charCount==charLimit){
       if(charChecking(temp)){
         System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
         charLimit=2;
     }
       else {  if(temp!="" && temp!=" ")                         //print invalid if 
         System.out.println("InvalidS token="+temp+ " at line number"+lineCounter);
       temp="";
       charLimit=2;
    }
       }
    continue;
    }
    else if(array[i]=='\"' && flagChar!=1 && flagComment!=1){
    if(temp!="" &&  temp.charAt(temp.length()-1)=='\\'){
    temp=temp+array[i];
    continue;
    }    
    if(flagString==0) { flagString=1;  }
    else if(flagString==1) { 
        flagString=0;
        temp=temp+array[i];
    }
    if(keywordChecking(temp)){
        System.out.println("("+temp+",_,"+lineCounter+")");
        temp="";
    }
    else if(datatypeChecking(temp)){
         System.out.println("(DT,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(identifierChecking(temp)){
         System.out.println("(ID,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='f'){
         System.out.println("(float_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='i'){
         System.out.println("(int_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(charChecking(temp)){
         System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(stringChecking(temp)){
         System.out.println("(String_const,"+temp+","+lineCounter+")");
         temp="";
         continue;
    }
    else  { if(temp!="")                         //print invalid if 
         System.out.println("InvalidS token="+temp+ " at line number"+lineCounter);
         temp="";
    }
    temp=temp+array[i];
    continue;
    }
    
    if(flagChar==1){
     if(temp.length()>1){
     if(temp.charAt(1)=='\\'){
     charLimit=3;
     }
     }
     
    temp=temp+array[i];
    charCount++;
      if(charCount==charLimit){
        flagChar=0;
    if(charChecking(temp)){
        System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else
    {  System.out.println("Invalidc token="+temp+ " at line number"+lineCounter);   
     temp="";  }   
    }      
    continue;
    } 
   if(flagComment!=1){ 
   temp=temp+array[i]; }
   }
    if(s.hasNextLine() || temp!=""){
      flagChar=0;
      flagString=0;
    if(keywordChecking(temp)){
        System.out.println("("+temp+",_,"+lineCounter+")");
        temp="";
       
    }
    else if(datatypeChecking(temp)){
         System.out.println("(DT,"+temp+","+lineCounter+")");
         temp="";
        
     }
    else if(identifierChecking(temp)){
         System.out.println("(ID,"+temp+","+lineCounter+")");
         temp="";
    }
    else if(digitConstChecking(temp)=='f'){
         System.out.println("(float_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(digitConstChecking(temp)=='i'){
         System.out.println("(int_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(charChecking(temp)){
         System.out.println("(char_const,"+temp+","+lineCounter+")");
         temp="";
     }
    else if(stringChecking(temp)){
         System.out.println("(String_const,"+temp+","+lineCounter+")");
         temp="";
     }
    
    else if(temp!="" && !temp.equals(" ")) {
         System.out.println("Invalida token="+temp+" at line number"+lineCounter);
        temp="";
    }
    }
  }  
        
    }
    
}
