wife(queen_elizabeth2, philip).
wife(diana, charles).
wife(camilla, charles).
wife(anne, captain_mark_philips).
wife(anne, vice_admiral_timothy_laurence).
wife(sarah, andrew).
wife(sophie,edward).
wife(kate, william).
wife(autumn_philips, peter_philips).
wife(zara_philips,mike_tindall).
son(charles, queen_elizabeth2).
son(william,diana).
son(harry,diana).
son(peter_philips, anne).
son(andrew, queen_elizabeth2).
son(edward, queen_elizabeth2).
son(james,sophie).
son(george,kate).
daughter(anne, queen_elizabeth2).
daughter(zara_philips,anne).
daughter(beatrice,sarah).
daughter(eugenie,sarah).
daughter(louise,sophie).
daughter(savannah, autumn_philips).
daughter(isla, autumn_philips).
daughter(mia_grace, zara_philips).
husband(X,Y):- wife(Y,X).
spouse(X,Y):- husband(X,Y);wife(X,Y).
child(X,Y):- son(X,Y);daughter(X,Y).
child(X,Y):- son(X,Z),spouse(Z,Y);daughter(X,Z),spouse(Z,Y).
parent(X,Y):- child(Y,X).
grandChild(X,Y):- child(X,Z),child(Z,Y).
grandParent(X,Y):- parent(X,Z),parent(Z,Y).
greatGrandParent(X,Y):- parent(X,Z),grandParent(Z,Y).
greatGrandChild(X,Y):- child(X,Z),grandChild(Z,Y).
brother(X,Y):- son(X,Z),child(Y,Z),not(X=Y).
sister(X,Y):- daughter(X,Z),child(Y,Z),not(X=Y).
uncle(X,Y):- brother(X,Z),parent(Z,Y).
uncle(X,Y):- husband(X,Z),aunt(Z,Y).
aunt(X,Y):- sister(X,Z),parent(Z,Y).
aunt(X,Y):- wife(X,Z),uncle(Z,Y).
brotherInLaw(X,Y):- brother(X,Z),spouse(Z,Y).
sisterInLaw(X,Y):- sister(X,Z),spouse(Z,Y).
brotherSisterInLaw(X,Y):- brotherInLaw(X,Y);sisterInLaw(X,Y).
neice(X,Y):- daughter(X,Z),wife(Z,V),brother(V,Y).
neice(X,Y):- daughter(X,Z),sister(Z,Y).
/**
* 2.c
* husband(X,sarah) -> andrew
* greatGrandChild(X, queen_elizabeth2) -> mia_grace;george;savannah;isla
* grandParent(X, zara_philips) -> queen_elizabeth2;philip
* brotherSisterInLaw(X,diana) -> andrew;edward;anne
* uncle(X,beatrice) -> charles;edward;vice_admiral_timothy_laurence;captain_mark_philips
* neice(X,charles) -> beatrice;eugenie;louise;zara_philips
*/
