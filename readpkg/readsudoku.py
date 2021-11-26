inp = []


def readinp():
    for i in range(0, 9):
        instr = input(f'Enter line {i+1}: ')
        inp.append(list(map(lambda x: int(x) if x.isnumeric() else None, list(instr))))
    return inp
