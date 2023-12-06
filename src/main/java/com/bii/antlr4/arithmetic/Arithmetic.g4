grammar Arithmetic;

parse: expr ;

expr: term (('+' | '-') term)*;

term: factor(('*' | '/') factor)*;

factor: NUMBER | '(' expr ')';

NUMBER: DIGIT+ ('.' DIGIT+)?;

DIGIT: [0-9];

WHITESPACE: [ \t\n\r] -> skip;

