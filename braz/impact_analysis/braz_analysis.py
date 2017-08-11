import re

from impact_analysis.utils import append_if_not_in
from impact_analysis.diff import changed_lines


class AnalysisResult:

    def __init__(self):
        self.macros_file = []
        self.lines_start = []
        self.lines_end = []
        self.impacted_macros = []
        self.changed_lines = []
        self.all_macros = []


class Braz:

    def __init__(self, old_file, new_file):
        self.old_file = old_file
        self.new_file = new_file

    def run(self, verbose=False):
        result = AnalysisResult()

        result.changed_lines = changed_lines(self.old_file, self.new_file)
        self._find_macro_lines(self.new_file, result)
        self._get_all_macros(result)
        self._get_changed_macros(result)

        if verbose:
            self._verbose(result)

        return result

    def _find_macro_lines(self, file, result):
        stack_macros = []
        stack_lines = []
        line = 1
        for l in file:
            l = l.replace('# ', '#')
            if l.strip().startswith('#if'):
                stack_macros.append(l)
                stack_lines.append(line)
            elif l.strip().startswith('#endif'):
                if len(stack_macros) != 0:
                    result.macros_file.append(stack_macros.pop())
                    result.lines_start.append(stack_lines.pop())
                    result.lines_end.append(line)
            line += 1

    def _get_changed_macros(self, result):
        for line in result.changed_lines:
            for i, ls in enumerate(result.lines_start):
                if result.lines_end[i] >= line >= ls:
                    append_if_not_in(result.impacted_macros,
                                     self._get_macros_in_line(
                                         result.macros_file[i]))

    def _is_a_clean_clean(self, macro):
        return ('&&' not in macro and '>' not in macro
                and '<' not in macro and '=' not in macro
                and 'defined"' not in macro and 'ined' not in macro
                and 'this_way"' not in macro and macro.strip() != '1'
                and macro.strip() != '0' and macro.strip() != 'BB_VER')

    def _clean_macro_line(self, macro_line):
        macro = macro_line.replace('!', '').replace('#if', '').replace(
            '#ifdef ', '').replace('#ifndef', '').replace('ndef', '').replace(
            'def', '').replace('ined', ' ').replace('|', ' ').replace(
            '  ', ' ').replace('(', '').replace(')', '')
        macro = re.sub(re.compile("/\*.*?\*/", re.DOTALL), "", macro)
        macro = re.sub(re.compile("//.*?\n"), "", macro)

        return macro

    def _get_macros_in_line(self, macro_line):
        macros_found = []
        macros = self._clean_macro_line(macro_line).split(' ')

        for m in macros:
            if (m and self._is_a_clean_clean(m)
               and m.strip not in macros_found):
                macros_found.append(m.strip())
            else:
                macros = str(m).split('&&')
                for n in macros:
                    if (n and self._is_a_clean_clean(n)
                       and n.strip not in macros_found):
                        macros_found.append(n.strip())

        return macros_found

    def _get_all_macros(self, result):
        for line in result.macros_file:
            append_if_not_in(result.all_macros, self._get_macros_in_line(line))

    def _verbose(self, result):
        print("Lines with #ifdefs: %r" % list(
            zip(result.macros_file,
                zip(result.lines_start, result.lines_end))))
        print("All macros: %r" % result.all_macros)
        print("Changed lines: %r" % result.changed_lines)
        print("Impacted macros: %r" % result.impacted_macros)