inp = []


def readinp():
    print(''' Read a 9x9 sudoku line-by-line by entering non-digits where the gaps are, like this:
        xxxx39xx8
        ''')
    for i in range(0, 9):
        instr = input(f'Enter line {i+1}: ')
        inp.append(list(map(lambda x: int(x) if x.isnumeric() else None, list(instr))))
    return inp
