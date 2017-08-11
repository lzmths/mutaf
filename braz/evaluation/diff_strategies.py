import subprocess


def _result_decorator(result, a, b):
    if result:
        print(a + " and " + b + " are EQUAL.")
    else:
        print(a + " and " + b + " are DIFFERENT.")


def _diff(command_line):
    try:
        subprocess.check_output(command_line, shell=True)
        return True
    except subprocess.CalledProcessError:
        return False


def diff_binary(a, b):
    return _diff("diff --binary " + a + " " + b)


def diff_preprocessor_out(a, b):
    return _diff("diff -uBI '#.*' " + a + " " + b)



