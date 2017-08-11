from evaluation.combination_strategies import impacted_feature


def _combination_to_params(combination):
    d_params = ""
    for c in combination:
        d_params += "-D" + c + " "

    return d_params


class CombinationRunner:

    def __init__(self, analysis_result, evaluation_executor, combination_strategy=impacted_feature):
        self.analysis_result = analysis_result
        self.evaluation_executor = evaluation_executor
        self.combination_strategy = combination_strategy
        self.combinations = self.combination_strategy(self.analysis_result)

    def run(self, files):
        different_combinations = []
        for i, combination in enumerate(self.combinations):
            if not self.evaluation_executor.run(files, _combination_to_params(combination)):
                different_combinations.append(list(combination))
        return different_combinations

    def clean(self):
        self.evaluation_executor.clean()

    def run_and_clean(self, files):
        different_combinations = self.run(files)
        self.clean()
        return different_combinations


