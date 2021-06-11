# Gomoku
Projekt pri programiranju 2 FMF. 

## Pravila
Gomoku je strateška igra drugače poznana tudi kot 5 v vrsto. Dva igralca izmenično polagata žetone na igralno ploščo velikosti 15x15. Zmaga prvi, ki mu uspe v vrsto postaviti 5 žetonov.

## Projekt
Koda za igro je napisana v Javi. Grafični vmesnik je napisan s pomočjo Java Swing. Glavni del projekta pa je razviti računlniškega igralca za igro. Uporabila sva več algoritmov za računalnikovo inteligenco naključni algoritem, minimax (z uporabo različnih ocen odigrane poteza) in alfa-beta.

### Končna verzija inteligence
Za končno verzijo inteligence je uporabljen nekoliko prilagojen alfabeta algoritem. Algoritem ne naredi zanke čez vse možne poteze ampak le čez skrajšan seznam kandidatov za poteze, ki so le polja, ki niso oddaljena za več kot dve polji od kakšnega že zasedenega polja in algoritem spreminja globino med 3 in 4 glede na stanje igre, da ne bi potreboval preveč časa (želela sva, da vsako potezo odigra najkasneje v petih sekundah).


