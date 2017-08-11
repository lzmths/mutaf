from difflib import SequenceMatcher, unified_diff

from utils.utils import append_if_not_in


def _interval_lines(start, stop):
    beginning = start + 1
    length = stop - start

    return [beginning + i for i in range(0, length)]


def changed_lines(a, b):
    result = []

    for group in SequenceMatcher(None, a, b).get_grouped_opcodes(n=0):
        first, last = group[0], group[-1]
        lines = _interval_lines(first[1], last[2]) + _interval_lines(first[3], last[4])
        append_if_not_in(result, lines)

    return result


def get_changes(a, b):
    changes = ''

    for d in unified_diff(a, b, n=0):
        changes += d

    return changes
