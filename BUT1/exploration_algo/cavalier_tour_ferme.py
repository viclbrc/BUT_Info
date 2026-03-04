import numpy as np

mouvements = [(2,1),(2,-1),(-2,1),(-2,-1),(1,2),(1,-2),(-1,2),(-1,-2)]

def backtrack(pos_x, pos_y, etape, chemin, echiquier, taille, profondeur=0):
    echiquier[pos_x, pos_y] = etape
    chemin.append((pos_x, pos_y))
    
    if etape == taille * taille:
        # Vérifier si on peut revenir au départ
        if any(pos_x+dx==chemin[0][0] and pos_y+dy==chemin[0][1] for dx,dy in mouvements):
            return True
        echiquier[pos_x, pos_y] = 0
        chemin.pop()
        return False
    
    # Heuristique de Warnsdorff : compter les sorties possibles
    coups = []
    for decalage_x, decalage_y in mouvements:
        nouveau_x = pos_x + decalage_x
        nouveau_y = pos_y + decalage_y
        
        if 0 <= nouveau_x < taille and 0 <= nouveau_y < taille and echiquier[nouveau_x, nouveau_y] == 0:
            nombre_sorties = sum(1 for ddx, ddy in mouvements 
                                if 0<=nouveau_x+ddx<taille and 0<=nouveau_y+ddy<taille 
                                and echiquier[nouveau_x+ddx, nouveau_y+ddy]==0)
            coups.append((nombre_sorties, nouveau_x, nouveau_y))
    
    coups.sort()
    limite = 2 if profondeur > 5 else len(coups)
    
    for _, nouveau_x, nouveau_y in coups[:limite]:
        if backtrack(nouveau_x, nouveau_y, etape+1, chemin, echiquier, taille, profondeur+1):
            return True
    
    echiquier[pos_x, pos_y] = 0
    chemin.pop()
    return False

# Programme principal
taille = 6
echiquier = np.zeros((taille, taille), dtype=int)
chemin = []

if backtrack(0, 0, 1, chemin, echiquier, taille):
    print(f"Cases visitées: {len(chemin)}/{taille*taille}\n")
    print(echiquier.astype(int))
else:
    print("✗ Aucun tour fermé trouvé.")
