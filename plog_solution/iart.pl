:- use_module(library(random)). % used to generate radom puzzles
:- use_module(library(lists)).
:- use_module(library(clpfd)).

get_vars_matrix(Board_Size, Board_Size, []) :- !.
get_vars_matrix(Board_Size, Row_Counter, [H|T]) :-
    length(H, Board_Size),
    Row_Counter1 is Row_Counter + 1,
    get_vars_matrix(Board_Size, Row_Counter1, T).

solve_zhed( Number_squares, [R-C]) :-
    get_vars_matrix(10, 0, Vars),
    solve(Vars, Number_squares, []),
    nth1(R, Vars, Row),
    element(C, Row, Square),
    nonvar(Square),
    nl, show_board_matrix(Vars), nl.


solve(Vars, [], _Visited_Number_squares).
solve(Vars, [R-C-N | Rest], Visited_Number_squares) :-
    apply(Vars, R-C-N),
    solve(Vars, Rest, [R-C-N, Visited_Number_squares]).

apply(Vars, R-C-N) :-
    C + N =< 10,
    apply_rigth(Vars,  R-C-N).

apply(Vars, R-C-N) :-
    C - N < 0,
    apply_left(Vars,  R-C-N).

apply(Vars, R-C-N) :-
    R - N < 0,
    apply_up(Vars,  R-C-N).

apply(Vars, R-C-N) :-
    R + N =< 10,
    apply_down(Vars,  R-C-N).


apply_rigth(Vars, R-C-N) :-
    nth1(R, Vars, Row),
    nth1(C, Row, Square),
    Square is 1,
    fill_rigth(Vars, R, C, N, 1).

fill_rigth(Vars, R, C, Number, Done) :-
    Done =< Number,
    nth1(R, Vars, Row),
    N is C + Done,
    nth1(N, Row, Square),
    var(Square),
    Square is 1,
    Done1 is Done + 1,
    fill_rigth(Vars, R, C, Number, Done1).

fill_rigth(Vars, R, C, Number, Done) :-
    Done =< Number,
    nth1(R, Vars, Row),
    N is C + Done,
    nth1(N, Row, Square),
    nonvar(Square),
    Number1 is Number + 1,
    fill_rigth(Vars, R, C, Number1, Done).

fill_rigth(_Vars, _R, _C, _Number, _Done).

apply_left(_Vars, []). 
apply_left(Vars, R-C-N) :-
    nth1(R, Vars, Row),
    nth1(C, Row, Square),
    Square is 1,
    fill_left(Vars, R, C, N, 1).

fill_left(Vars, R, C, Number, Done) :-
    Done =< Number,
    nth1(R, Vars, Row),
    N is C - Done,
    nth1(N, Row, Square),
    Square is 1,
    Done1 is Done + 1,
    fill_left(Vars, R, C, Number, Done1).

fill_left(_Vars, _R, _C, _Number, _Done).

apply_down(_Vars, []). 
apply_down(Vars, R-C-N) :-
    nth1(R, Vars, Row),
    nth1(C, Row, Square),
    Square is 1,
    fill_down(Vars, R, C, N, 1).

fill_down(Vars, R, C, Number, Done) :-
    Done =< Number,
    N is R + Done,
    nth1(N, Vars, Row),
    nth1(C, Row, Square),
    Square is 1,
    Done1 is Done + 1,
    fill_down(Vars, R, C, Number, Done1).

fill_down(_Vars, _R, _C, _Number, _Done).

apply_up(_Vars, []). 
apply_up(Vars, R-C-N) :-
    nth1(R, Vars, Row),
    nth1(C, Row, Square),
    Square is 1,
    fill_up(Vars, R, C, N, 1).

fill_up(Vars, R, C, Number, Done) :-
    Done =< Number,
    N is R - Done,
    nth1(N, Vars, Row),
    nth1(C, Row, Square),
    Square is 1,
    Done1 is Done + 1,
    fill_up(Vars, R, C, Number, Done1).

fill_up(_Vars, _R, _C, _Number, _Done).


%%%%%%%%%%%%%%%                    SHOW                     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
show_board_matrix([]) :- !.
show_board_matrix([Row|Rest]) :-
    show_row(Row), 
    nl,
    show_board_matrix(Rest).

show_row([]) :- !.

show_row([Col|Rest]) :-
    var(Col),
    write('0'), write(' '),
    show_row(Rest).

show_row([1 |Rest]) :-
    write('1'), write(' '),
    show_row(Rest).

show_row([-1 |Rest]) :-
    write('-1'), write(' '),
    show_row(Rest).
