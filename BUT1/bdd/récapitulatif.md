Groupe B14

Consignes :  

    Stocker les relevés de compteur tout au long de l'année 
    stocker les différentes formules tarifaires des fournisseur / prix au KW/h (variant selon le jour parmi 7 et selon l'heure)
    Calculer la facture potentielle d'un contrat sur une période donnée en agrégeant consommations par créneaux et en appliquant les tarifs correspondant (conso + abonnement)
    Gérer les changements de formule ou de fournisseur tout en historisant les contrats et période

**2.1** Propositions tarifaires :

    Offres varie le prix de l'abonnement : 
        Tarif BASE : pas de distinction de jour, ni d'horaire
        Tarif heures creuses (HC) : Moins cher sur les horaires ou la consommations est la plus faible et la production excède 
        Tarif heures pleines (HP) : Horaires où les consommations sont les plus fortes 
        Contrat signé à une date précise entre client et fournisseur d'élec pour un point de livraison (PDL)
        Signature = Le contrat fixe une offre tarifaire (Peut être changé au cours du temps : soit par choix du client, soit par remaniement de son catalogue d'offres par le fournisseur. Contrat = même MAIS offre change à partir d'une date donnée)
        La fin de souscription d'une offre tarifaire est fixée par la souscription de la nouvelle
        Historique des offres facturées au contrat des clients est conservé à une date donnée sur un PDL
        Une seule offre tarifaire doit pouvoir être active 
        Fin de contrat = résiliation totale pour X raison met fin à la souscription de l'offre en cours

**2.2** Le rôle d'Enedis et des fournisseurs :

    Enedis = gestionnaire du réseau de distribution. Définit plages horaires techniques des HC sur chaque PDL en fonction des contraintes locales du réseau. c'est donc NORMALEMENT la prérogative d'Enedis de fixer les HC et HP sur chaque compteur électrique du client 

    Certains fournisseurs (Ex : EDF) proposent sur certaines offres différencier les saisons été et hiver. Les tarifs créneaux des HC et HP varient alors en fonction des saisons (Heures creuses virtuelles)

**2.3** Principes des heures creuses virtuelles :

    1. Offres à tarifications dynamiques (ou heure creuses virtuelles)
        
        Ces offres reposent sur les prix spot de l'élec donc les marché comme EPEX Spot
        Le fournisseur peut proposer des tarifs réduits à certaines heures (par exemple l'aprem l'été quand le soleil abonde) même si Enedis ne considère pas ces heures comme creuses
        Compteur Linky permet de mesurer la conso demi-heure par demi-heure 

    2. Pilotage via équipements connectés 

        Certains fournisseurs intègrent des systèmes de pilotage (via box ou applications) qui déclenchent automatiquement les appareils énergivores (chauffe-eau / lave-linge / etc...) pendant les heures où le tarif est le plus bas 
        Cela permet de bénéficier de tarifs avantageux même si les plages HC d'Enedis ne sont pas activées à ce moment-là

**2.4** Synthèse des variations possibles dans les offres tarifaires :

    Une offre tarifaire propose différentes puissances maximales pour chaque PDL. La valeur de la puissance peut faire varier le prix de l'abonnement mais aussi le prix du kilowattheure.
    Au sein d'une offre, plusieurs périodes de consommations différentes au long de la semaine (HC et HP par exemple) ou pas (tarif Base). 
    Toute période possède une heure de début et une heure de fin dans une journée donnée (lundi à dimanche).
    Ces périodes peuvent survenir sur plusieurs créneaux de la journée. Par exemple, les HC sont fixées de 00:00 à 5:59 et de 23:00 à 00:59 le lundi. 
    Ces créneaux peuvent varier selon la saison (HC été et HC hiver) selon les fournisseurs et abonnements. Pour chaque période de consommation dans une offre tarifaire, un prix au kilowattheure est défini. 
    Les offres des différents fournisseurs sont commercialisées à partir d'une date dite "de lancement" et peuvent prendre fin à tout moment. La fin de l'application de l'offre marque alors la fin de toute souscription de cette offre pour tous les clients. On suppose que les clients encore sous contrat ont alors opté pour une nouvelle offre.

**2.5** Principe des relevés de compteurs Linky : 

    L'application fournie ne fonctionnera a priori qu'avec des relevés effectués par le compteur Linky. Seul cet équipement, avec des relevés toutes les demi-heures fournit la précision nécessaire pour appliquer cette diversité de tarifs, notamment avec le principe des HC virtuelles.
    Les relevés consistent en une date, une heure entre 00:00 et 23:30 avec un incrément de 30 minutes, et une consommation sur les créneaux en KwH.

Classes possibles :

    Tarifs : HC / HP / Base / HC virutelles
    Fournisseurs : EDF / Enedis / Total / etc...
    Date : Heure / Jour 
    Saisons : Hiver / été





