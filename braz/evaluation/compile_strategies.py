import os
import subprocess

OPTIMIZATION = "-O2"


def _extract_features(filename, command):
    filename = filename.split('/')[-1].replace('.c', '')
    parts = command.split(' ')
    for part in parts:
        if str(part).startswith('-D'):
            filename += part.replace('-D', '-')

    return filename + ".out"


def _result_filename(filename, command):
    return _extract_features(filename, command)


def _compile(diff, files, command, path):
    object_files = []
    for i, c_file in enumerate(files):
        result_file = os.path.join(path, _result_filename(c_file, command))
        subprocess.call(command % (c_file, result_file), shell=True,
                        stderr=subprocess.DEVNULL)
        object_files.append(result_file)
    return diff(object_files[0], object_files[1])


def compile_for_tce(diff, files, params, path=''):
    gcc_cmd = 'gcc ' + params + OPTIMIZATION + ' -s %s -o %s'
    return _compile(diff, files, gcc_cmd, path)


def compile_preprocessor_only(diff,files, params, path=''):
    gcc_cmd = 'gcc -E ' + params + OPTIMIZATION + ' -s %s -o %s'
    return _compile(diff, files, gcc_cmd, path)
