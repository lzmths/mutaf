import os
import shutil


def _create_directory(path):
    path = "temp" if path is None else path
    if os.path.exists(path):
        shutil.rmtree(path)
    os.makedirs(path)
    return path


class EvaluationExecutor:

    def __init__(self, compile_strategy, diff_strategy, path=None):
        self.compile_strategy = compile_strategy
        self.diff_strategy = diff_strategy
        self.path = _create_directory(path)

    def run(self, files, params):
        return self.compile_strategy(self.diff_strategy, files, params, self.path)

    def clean(self):
        if self.path and os.path.exists(self.path):
            shutil.rmtree(self.path)



