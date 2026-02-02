import numpy as np

tab = [['A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'G1', 'H1'],
       ['A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2'],
       ['A3', 'B3', 'C3', 'D3', 'E3', 'F3', 'G3', 'H3'],
       ['A4', 'B4', 'C4', 'D4', 'E4', 'F4', 'G4', 'H4'],
       ['A5', 'B5', 'C5', 'D5', 'E5', 'F5', 'G5', 'H5'],
       ['A6', 'B6', 'C6', 'D6', 'E6', 'F6', 'G6', 'H6'],
       ['A7', 'B7', 'C7', 'D7', 'E7', 'F7', 'G7', 'H7'],
       ['A8', 'B8', 'C8', 'D8', 'E8', 'F8', 'G8', 'H8']]

# 0 = non visité, 1 = visité
echiquier = np.zeros((8, 8), dtype=int)

# Les mouvements possibles du cavalier
mouvements = [(2, 1), (2, -1), (-2, 1), (-2, -1),
             (1, 2), (1, -2), (-1, 2), (-1, -2)]

def estValide(x: int, y: int):
    '''
    Vérifie si une position est valide (dans les limites et non visitée)
    '''
    if 0 <= x < 8 and 0 <= y < 8:
        if echiquier[x][y] == 0:  # Si case non visitée
            return True
    return False

def dfs_cavalier(x: int, y: int, compteur: int, chemin: list):
    '''
    DFS avec Backtracking pour résoudre le problème du cavalier.
    
    Paramètres:
    - x, y : position actuelle
    - compteur : nombre de cases visitées
    - chemin : liste des cases visitées dans l'ordre
    '''
    # Marquer la case actuelle comme visitée
    echiquier[x][y] = compteur
    chemin.append(tab[x][y])
    
    # Condition d'arrêt : si toutes les cases sont visitées (8*8 = 64)
    if compteur == 64:
        return True
    
    # Tester tous les mouvements possibles du cavalier
    for dx, dy in mouvements:
        nouveauX, nouveauY = x + dx, y + dy
        
        if estValide(nouveauX, nouveauY):
            # Appel récursif
            if dfs_cavalier(nouveauX, nouveauY, compteur + 1, chemin):
                return True  # Solution trouvée
    
    # Backtracking : remet la case à 0 (non visitée) car impasse
    echiquier[x][y] = 0
    chemin.pop()
    return False

# Lancer l'algorithme à partir de la case (0, 0)
chemin = []
if dfs_cavalier(0, 0, 1, chemin):
    print("Solution trouvée !")
    print(f"Chemin du cavalier ({len(chemin)} cases) :")
    print(chemin)
    print("\nMatrice de visite (ordre de passage) :")
    print(echiquier)
else:
    print("Aucune solution trouvée.")