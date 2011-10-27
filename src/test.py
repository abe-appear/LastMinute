import random

def visaInformation():
    print("""
             ------------------------------------------------
             |                                              |
             |          VÄLKOMMEN TILL TIVOLIN!!            |
             |                                              |
             ------------------------------------------------""")
# tablighat baraye Insane
def reklamInsane():
    print("""INSANE åker de allra modigaste som vill uppleva galna G-krafter och vilda supersnurrar! \n""")

# tablighat baraye Twister
def reklamTwister():
    print("""TWISTER är en härligt vriden berg- och dalbana i trä som bjuder alla av det rätta virket på en åkupplevelse utöver det vanliga. \n""")

# tablighat baraye Jetline
def reklamJetline():
    print("""JETLINE är en berg- och dalbana full av action på höga höjder och kurvor som får fler än 1 miljon åkare varje säsong att jubla.  \n""")


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
Det har uppstått ett problem. Vi åtgärdar detta så fort vi kan. Tack för ditt tålamod!""")
        print(""".
.
.
.
.
.
.
.
""")
        print("Nu kör vi igång igen :D")
        rolig.haKul()

    # payan
    def avsked(self):
        print()
        print("Tack för idag. Vi hade riktigt kul!")

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
print("Du ser attraktionerna sorterad efter spänning, i stigande ordning:")
skrivListan(lista)

reklamInsane()
reklamTwister()
reklamJetline()

val=input("Vilken attraktion vill du åka? Insane, Twister eller Jetline? ")
spanning=0
rolig=Attraktion(val,spanning)
while val:
	# zarfiate baziaye mokhtalef fargh mikone. inja barname betore etefaghi tain mikone ke ja vase savar shodan hast ya na.
    plats_t = random.randrange(1,5)  # Antal passagerare för Twister = 4
    plats_j = random.randrange(1,15) # Antal passagerare för Jetline = 14
    plats_i = random.randrange(1,9)  # Antal passagerare för Insane = 8

    if val[0]=="T": # Attraktionen Twister
        spanning=6
        if plats_t>=4:
            print("Det finns tyvärr inga lediga platser.")
        else:
            langdkrav = int(input("Hur lång är du (cm)? "))
            if langdkrav>=120: # Längdkravet för Twister = 120 cm
                haverera=random.randrange(1,11)
                if haverera>=10: # Sannolikheten att attraktionen havererar är 10 procent.
                    rolig.krasch()
                else:
                    rolig.haKul()
            else:
                print("Du är tyvärr för kort för att kunna få åka.")

    elif val[0]=="J": # Attraktionen Jetline
        spanning=9
        if plats_j>=14:
            print("Det finns tyvärr inga lediga platser.")
        else:
            langdkrav = int(input("Hur lång är du (cm)? "))
            if langdkrav>=140: # Längdkravet för Jetline = 140 cm
                haverera=random.randrange(1,11)
                if haverera>=8: # Sannolikheten att attraktionen havererar är 30 procent.
                    rolig.krasch()
                else:
                    rolig.haKul()
            else:
                print("Du är tyvärr för kort för att kunna få åka.")

    elif val[0]=="I": #Attraktionen Insane
        spanning=3
        if plats_i>=8:
            print("Det finns tyvärr inga lediga platser.")
        else:
            langdkrav = int(input("Hur lång är du (cm)? "))
            if langdkrav>=120: # Längdkravet för Insane = 120 cm
                haverera=random.randrange(1,11)
                if haverera>=9: # Sannolikheten att attraktionen havererar är 20 procent.
                    rolig.krasch()
                else:
                    rolig.haKul()
            else:
                print("Du är tyvärr för kort för att kunna få åka.")
    else:
        print("Hursa? ")
    val=input("\n(Tryck Enter om du har åkt färdigt.) \n Vilken attraktion vill du åka? Insane, Twister eller Jetline? ")
rolig.avsked()