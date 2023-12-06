package com.bii.antlr4.arithmetic;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

public class EvalVisitor extends ArithmeticBaseVisitor<Double>{


    @Override
    public Double visitExpr(ArithmeticParser.ExprContext ctx) {
        Double result = visit(ctx.term(0));
        for(int i = 1; i < ctx.term().size(); i++){
            String operator = ctx.getChild(2 * i - 1).getText();
            Double term = visit(ctx.term(i));
            if(operator.equals('+')){
                result += term;
            }else{
                result -= term;
            }
        }
        return result;
    }

    @Override
    public Double visitTerm(ArithmeticParser.TermContext ctx) {
        Double result = visit(ctx.factor(0));
        for(int i = 1; i < ctx.factor().size(); i++){
            String operator = ctx.getChild(2 * i - 1).getText();
            Double factor = visit(ctx.factor(i));
            if(operator.equals('*')){
                result *= factor;
            }else {
                result /= factor;
            }
        }
        return result;
    }

    // 重写 visitFactor 方法，用于计算数字和括号内的表达式
    @Override
    public Double visitFactor(ArithmeticParser.FactorContext ctx) {
        if(ctx.NUMBER() != null){
            // 如果是一个数字，直接返回其值
            return Double.parseDouble(ctx.NUMBER().getText());
        }else {
            // 如果是括号内的表达式，递归调用 visit 方法
            return visit(ctx.expr());
        }
    }

    public static void main(String[] args) {
        String input = "1+2*(1-2)";
        // 词法解析器, 用于转换标记
        ArithmeticLexer lexer = new ArithmeticLexer(CharStreams.fromString(input));
        // 创建标记流, 用于将标记传递至解析器
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 创建解析器, 将标记转换为 AST
        ArithmeticParser parser = new ArithmeticParser(tokens);
        // 创建 AST 遍历器，用于计算表达式的值
        ArithmeticParser.ParseContext parse = parser.parse();

        // 创建 AST 遍历器, 用于计算表达式的值
        EvalVisitor evalVisitor = new EvalVisitor();
        double result = evalVisitor.visit(parse);
        System.out.print(result);

    }
}
