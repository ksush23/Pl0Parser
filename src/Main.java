import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormatSymbols;
import java.util.Scanner;

public class Main {

    public enum Symbol{
        ident, number, lparen, rparen, times, slash, plus, minus, oddsym, eql, neq, lss, leq, gtr, gtreq,
        becomes, callsym, beginsym, semicolon, endsym, ifsym, thensym, whilesym, dosym, constsym, comma,
        procsym, period, varsym
    }

    public static Symbol sym;
    public static String file = "";
    public static int i = 0;

    public static void main(String[] args) throws FileNotFoundException {
        FileReader fileReader = new FileReader("input");
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNextLine()){
            file += scanner.nextLine();
        }

        getSymbol();
        block();
        except(Symbol.period);
        System.out.println("Accepted");
    }

    public static void getSymbol(){
        char c = file.charAt(i);
        while ((int)c <= 32){
            i++;
            c = file.charAt(i);
        }
        if (c == '(' || c == '{' || c == '[') {
            sym = Symbol.lparen;
            i++;
            return;
        }
        if (c == ')' || c == '}' || c == ']') {
            sym = Symbol.rparen;
            i++;
            return;
        }
        if (c == '+') {
            sym = Symbol.plus;
            i++;
            return;
        }
        if (c == '-') {
            sym = Symbol.minus;
            i++;
            return;
        }
        if (c == ';') {
            sym = Symbol.semicolon;
            i++;
            return;
        }
        if (c == ',') {
            sym = Symbol.comma;
            i++;
            return;
        }
        if (c == '.') {
            sym = Symbol.period;
            i++;
            return;
        }
        if (c == '*') {
            sym = Symbol.times;
            i++;
            return;
        }
        if (c == '/') {
            sym = Symbol.slash;
            i++;
            return;
        }
        if (c == '=') {
            i++;
            sym = Symbol.eql;
            return;
        }
        if (c == '<'){
            if (file.charAt(i + 1) == '='){
                i += 2;
                sym = Symbol.leq;
                return;
            }
            else {
                sym = Symbol.lss;
                i++;
                return;
            }
        }
        if (c == '>'){
            if (file.charAt(i + 1) == '='){
                i += 2;
                sym = Symbol.gtreq;
                return;
            }
            else {
                sym = Symbol.gtr;
                i++;
                return;
            }
        }
        if (c == '!'){
            c = file.charAt(i + 1);
            if (c == '=') {
                i += 2;
                sym = Symbol.neq;
                return;
            }
        }
        if (c == ':'){
            c = file.charAt(i + 1);
            if (c == '=') {
                i += 2;
                sym = Symbol.becomes;
                return;
            }
        }
        if ((int)c >= 48 && (int)c <= 57){
            do{
                i++;
                c = file.charAt(i);
            }while((int)c >= 48 && (int)c <= 57);
            sym = Symbol.number;
            return;
        }
        if (c == 'o'){
            if (file.charAt(i + 1) == 'd' && file.charAt(i + 2) == 'd') {
                i += 3;
                sym = Symbol.oddsym;
                return;
            }
        }
        if (c == 'c'){
            if (file.charAt(i + 1) == 'o' && file.charAt(i + 2) == 'n' && file.charAt(i + 3) == 's'
            && file.charAt(i + 4) == 't'){
                i += 5;
                sym = Symbol.constsym;
                return;
            }
            if (file.charAt(i + 1) == 'a' && file.charAt(i + 2) == 'l' && file.charAt(i + 3) == 'l'){
                i += 4;
                sym = Symbol.callsym;
                return;
            }
        }
        if (c == 'i'){
            if (file.charAt(i + 1) == 'f'){
                i += 2;
                sym = Symbol.ifsym;
                return;
            }
        }
        if (c == 't'){
            if (file.charAt(i + 1) == 'h' && file.charAt(i + 2) == 'e' && file.charAt(i + 3) == 'n'){
                i += 4;
                sym = Symbol.thensym;
                return;
            }
        }
        if (c == 'b'){
            if (file.charAt(i + 1) == 'e' && file.charAt(i + 2) == 'g' && file.charAt(i + 3) == 'i'
            && file.charAt(i + 4) == 'n'){
                i += 5;
                sym = Symbol.beginsym;
                return;
            }
        }
        if (c == 'e'){
            if (file.charAt(i + 1) == 'n' && file.charAt(i + 2) == 'd'){
                i += 3;
                sym = Symbol.endsym;
                return;
            }
        }
        if (c == 'w'){
            if (file.charAt(i + 1) == 'h' && file.charAt(i + 2) == 'i' && file.charAt(i + 3) == 'l'
            && file.charAt(i + 4) == 'e'){
                i += 5;
                sym = Symbol.whilesym;
                return;
            }
        }
        if (c == 'd'){
            if (file.charAt(i + 1) == 'o'){
                i += 2;
                sym = Symbol.dosym;
                return;
            }
        }
        if (c == 'p'){
            if (file.charAt(i + 1) == 'r' && file.charAt(i + 2) == 'o' && file.charAt(i + 3) == 'c'
                    && file.charAt(i + 4) == 'e' && file.charAt(i + 5) == 'd' && file.charAt(i + 6) == 'u'
            && file.charAt(i + 7) == 'r' && file.charAt(i + 8) == 'e'){
                i += 9;
                sym = Symbol.procsym;
                return;
            }
        }
        if (c == 'v'){
            if (file.charAt(i + 1) == 'a' && file.charAt(i + 2) == 'r'){
                i += 3;
                sym = Symbol.varsym;
                return;
            }
        }
        sym = Symbol.ident;
        i++;
    }

    public static int accept(Symbol s){
        if (sym == s){
            if (sym == Symbol.period){
                return 1;
            }
            getSymbol();
            return 1;
        }
        return 0;
    }

    public static int except(Symbol s){
        if (accept(s) == 1)
            return 1;
        System.out.println("expect:unexpected symbol");
        return 0;
    }

    public static void factor(){
        if (accept(Symbol.ident) == 1){

        }
        else if (accept(Symbol.number) == 1){

        }
        else if (accept(Symbol.lparen) == 1){
            expression();
            except(Symbol.rparen);
        }
        else{
            System.out.println("factor: syntax error");
            getSymbol();
        }
    }

    public static void term(){
        factor();
        while(sym == Symbol.times || sym == Symbol.slash){
            getSymbol();
            factor();
        }
    }

    public static void expression(){
        if (sym == Symbol.plus || sym == Symbol.minus){
            getSymbol();
        }
        term();
        while(sym == Symbol.plus || sym == Symbol.minus){
            getSymbol();
            term();
        }
    }

    public static void condition(){
        if (accept(Symbol.oddsym) == 1){
            expression();
        }
        else{
            expression();
            if (sym == Symbol.eql || sym == Symbol.neq || sym == Symbol.lss || sym == Symbol.leq
            || sym == Symbol.gtr || sym == Symbol.gtreq){
                getSymbol();
                expression();
            }
            else{
                System.out.println("condition: invalid operator");
                getSymbol();
            }
        }
    }

    public static void statement(){
        if (accept(Symbol.ident) == 1){
            except(Symbol.becomes);
            expression();
        }
        else if (accept(Symbol.callsym) == 1){
            except(Symbol.ident);
        }
        else if (accept(Symbol.beginsym) == 1){
            do{
                statement();
            }while (accept(Symbol.semicolon) == 1);
            except(Symbol.endsym);
        }
        else if(accept(Symbol.ifsym) == 1){
            condition();
            except(Symbol.thensym);
            statement();
        }
        else if(accept(Symbol.whilesym) == 1){
            condition();
            except(Symbol.dosym);
            statement();
        }
    }

    public static void block(){
        if (accept(Symbol.constsym) == 1){
            do{
                except(Symbol.ident);
                except(Symbol.eql);
                except(Symbol.number);
            }while(accept(Symbol.comma) == 1);
            except(Symbol.semicolon);
        }
        if (accept(Symbol.varsym) == 1){
            do{
                except(Symbol.ident);
            }while(accept(Symbol.comma) == 1);
            except(Symbol.semicolon);
        }
        while (accept(Symbol.procsym) == 1){
            except(Symbol.ident);
            except(Symbol.semicolon);
            block();
            except(Symbol.semicolon);
        }
        statement();
    }
}
