from itertools import permutations, chain
from pprint import pprint
from readpkg import readsudoku

input = readsudoku.readinp()


def isvalid(entry, position):
    for i in range(0, 9):
        col = list(zip(*input))
        if ((input[position][i] is not None and entry[i] != input[position][i])
                or (input[position][i] is None and entry[i] in input[position])
                or (input[position][i] is None and entry[i] in col[i])):
            return False
    return True


puzzel = [[]] * 9
for i in range(0, 9):
    puzzel[i] = [x for x in permutations(range(1, 10)) if isvalid(x, i)]


for entry0 in puzzel[0]:
    for entry1 in puzzel[1]:
        if not all(len(set(x)) == 2 for x in set(zip(entry0, entry1))):
            continue
        for entry2 in puzzel[2]:
            if not len(set(chain(entry0[:3], entry1[:3], entry2[:3]))) == 9:
                continue
            if not len(set(chain(entry0[3:6], entry1[3:6], entry2[3:6]))) == 9:
                continue
            if not len(set(chain(entry0[6:], entry1[6:], entry2[6:]))) == 9:
                continue
            for entry3 in puzzel[3]:
                if not all(len(set(x)) == 4 for x in set(zip(entry0, entry1, entry2, entry3))):
                    continue
                for entry4 in puzzel[4]:
                    if not all(len(set(x)) == 5 for x in set(zip(entry0, entry1, entry2, entry3, entry4))):
                        continue
                    for entry5 in puzzel[5]:
                        if not len(set(chain(entry3[:3], entry4[:3], entry5[:3]))) == 9:
                            continue
                        if not len(set(chain(entry3[3:6], entry4[3:6], entry5[3:6]))) == 9:
                            continue
                        if not len(set(chain(entry3[6:], entry4[6:], entry5[6:]))) == 9:
                            continue
                        for entry6 in puzzel[6]:
                            if not all(len(set(x)) == 7
                                       for x in set(zip(entry0, entry1, entry2, entry3, entry4, entry5, entry6))):
                                continue
                            for entry7 in puzzel[7]:
                                if not all(len(set(x)) == 8 for x in
                                           set(zip(entry0, entry1, entry2, entry3, entry4, entry5, entry6, entry7))):
                                    continue
                                for entry8 in puzzel[8]:
                                    e = [entry0, entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8]
                                    if not all(len(set(x)) == 9 for x in zip(*e)):
                                        continue
                                    pprint(e)
