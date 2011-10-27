import random

def visaInformation():
    print("""
             ------------------------------------------------
             |                                              |
             |          V�LKOMMEN TILL TIVOLIN!!            |
             |                                              |
             ------------------------------------------------""")
# tablighat baraye Insane
def reklamInsane():
    print("""INSANE �ker de allra modigaste som vill uppleva galna G-krafter och vilda supersnurrar! \n""")

# tablighat baraye Twister
def reklamTwister():
    print("""TWISTER �r en h�rligt vriden berg- och dalbana i tr� som bjuder alla av det r�tta virket p� en �kupplevelse ut�ver det vanliga. \n""")

# tablighat baraye Jetline
def reklamJetline():
    print("""JETLINE �r en berg- och dalbana full av action p� h�ga h�jder och kurvor som f�r fler �n 1 miljon �kare varje s�song att jubla.  \n""")


class Attraktion:

    def __init__(self,attraktionsnamn,spanning):
        self.namn = attraktionsnamn
        self.magpirrfaktor = spanning

    def __str__(self):
        return "\n" + self.namn + " - " + str(self.magpirrfaktor) + "\n"


    # hayejane bazio neshoon mide
    def haKul(self):
        print()
        for i in range(spanning):
            print("WOOHOO" , end=" ")
        print("\n")


    # bazi gir mikone
    def krasch(self):
        print("""\n ----------KRASCH----------
Det har uppst�tt ett problem. Vi �tg�rdar detta s� fort vi kan. Tack f�r ditt t�lamod!""")
        print(""".
.
.
.
.
.
.
.
""")
        print("Nu k�r vi ig�ng igen :D")
        rolig.haKul()

    # payan
    def avsked(self):
        print()
        print("Tack f�r idag. Vi hade riktigt kul!")

#  -------  inja class tamoom mishe ---------


# liste baziaro neshon mide
def skrivListan(lista):
    for i in lista:
        print(i, end=" ")
    print("\n")


#  --------  inja barname shoroo mishe ---------
visaInformation()
lista = [Attraktion("Twister",6), Attraktion("Jetline",9), Attraktion("Insane",3)]
lista.sort(key = lambda attraktion:attraktion.magpirrfaktor)
print("Du ser attraktionerna sorterad efter sp�nning, i stigande ordning:")
skrivListan(lista)

reklamInsane()
reklamTwister()
reklamJetline()

val=input("Vilken attraktion vill du �ka? Insane, Twister eller Jetline? ")
spanning=0
rolig=Attraktion(val,spanning)
while val:
	# zarfiate baziaye mokhtalef fargh mikone. inja barname betore etefaghi tain mikone ke ja vase savar shodan hast ya na.
    plats_t = random.randrange(1,5)  # Antal passagerare f�r Twister = 4
    plats_j = random.randrange(1,15) # Antal passagerare f�r Jetline = 14
    plats_i = random.randrange(1,9)  # Antal passagerare f�r Insane = 8

    if val[0]=="T": # Attraktionen Twister
        spanning=6
        if plats_t>=4:
            print("Det finns tyv�rr inga lediga platser.")
        else:
            langdkrav = int(input("Hur l�ng �r du (cm)? "))
            if langdkrav>=120: # L�ngdkravet f�r Twister = 120 cm
                haverera=random.randrange(1,11)
                if haverera>=10: # Sannolikheten att attraktionen havererar �r 10 procent.
                    rolig.krasch()
                else:
                    rolig.haKul()
            else:
                print("Du �r tyv�rr f�r kort f�r att kunna f� �ka.")

    elif val[0]=="J": # Attraktionen Jetline
        spanning=9
        if plats_j>=14:
            print("Det finns tyv�rr inga lediga platser.")
        else:
            langdkrav = int(input("Hur l�ng �r du (cm)? "))
            if langdkrav>=140: # L�ngdkravet f�r Jetline = 140 cm
                haverera=random.randrange(1,11)
                if haverera>=8: # Sannolikheten att attraktionen havererar �r 30 procent.
                    rolig.krasch()
                else:
                    rolig.haKul()
            else:
                print("Du �r tyv�rr f�r kort f�r att kunna f� �ka.")

    elif val[0]=="I": #Attraktionen Insane
        spanning=3
        if plats_i>=8:
            print("Det finns tyv�rr inga lediga platser.")
        else:
            langdkrav = int(input("Hur l�ng �r du (cm)? "))
            if langdkrav>=120: # L�ngdkravet f�r Insane = 120 cm
                haverera=random.randrange(1,11)
                if haverera>=9: # Sannolikheten att attraktionen havererar �r 20 procent.
                    rolig.krasch()
                else:
                    rolig.haKul()
            else:
                print("Du �r tyv�rr f�r kort f�r att kunna f� �ka.")
    else:
        print("Hursa? ")
    val=input("\n(Tryck Enter om du har �kt f�rdigt.) \n Vilken attraktion vill du �ka? Insane, Twister eller Jetline? ")
rolig.avsked()