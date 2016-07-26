footpath(52,53).
footpath(52,54).
footpath(52,57).
footpath(52,62).
footpath(52,68).
footpath(52,59).
footpath(52,60).
footpath(52,83).
footpath(53,57).
footpath(53,54).
footpath(76,69).
footpath(53,57).
footpath(65,83).
footpath(54,57).
footpath(56,57).
footpath(56,58).
footpath(57,58).
footpath(57,59).
footpath(57,62).
footpath(57,59).
footpath(57,68).
footpath(57,60).
footpath(57,83).
footpath(58,59).
footpath(68,65).
footpath(59,60).
motorway(54,62).
motorway(54,56).
motorway(54,59).
motorway(54,68).
motorway(54,60).
motorway(54,83).
motorway(54,56).
motorway(59,60).
motorway(59,68).
motorway(59,83).
motorway(59,62).
motorway(60,62).
motorway(60,83).
motorway(60,68).
motorway(62,83).
motorway(62,63).
motorway(62,64).
motorway(62,68).
motorway(62,76).
motorway(62,66).
motorway(68,66).
motorway(68,63).
motorway(68,64).
motorway(68,76).
motorway(63,64).
motorway(63,76).
motorway(63,66).
motorway(63,69).
motorway(64,76).
motorway(64,66).
motorway(64,69).
motorway(53,59).
motorway(53,68).
motorway(53,60).
motorway(53,83).
motorway(76,66).
motorway(53,54).
motorway(53,62).
motorway(76,69).
motorway(66,69).
isMotor(X,Y):- motorway(X,Y);motorway(Y,X).
isFoot(X,Y):- footpath(X,Y);footpath(Y,X).
direct(X,Y):- isFoot(X,Y);isMotor(X,Y).
route(X,Y):- direct(X,Y).
route(X,Y):- direct(X,Z),route(Z,Y).
motorRoute(X,Y):- direct(X,Y).
motorRoute(X,Y):- direct(X,Z),motorRoute(Z,Y).
footpathRoute(X,Y):- direct(X,Y).
footpathRoute(X,Y):- direct(X,Z),footpathRoute(Z,Y).
/**
 *	1.b
 * route(56,60)
 * route(60,56)
 * route(52,58)
 * route(58,68)
 * route(53,58)
 * footpathRoute(56,59)
 * 
 * 1.c
 * is there a route from hall 56 to hall 60 -> route(56,60)
 * is there a route from hall 60 to hall 56 -> route(60,56)
 * is there a route from hall 52 to hall 58 -> route(52,58)
 * is there a footpath route from hall 56 to hall 59 -> footpathRoute(56,59)
 * is there a motorway from hall 53 to hall 60 -> motorway(53,60)
 * is there a footpath from hall 52 to hall 62 -> footpath(53,57)
 */


