import itertools


def impacted_feature(analysis_result):
    combinations_params = []

    for i in range(len(analysis_result.impacted_macros) + 1):
        for combination in itertools.combinations(analysis_result.impacted_macros, i):
            combinations_params.append(combination)

    return combinations_params


def all_features(analysis_result):
    combinations_params = []

    for i in range(len(analysis_result.all_macros) + 1):
        for combination in itertools.combinations(analysis_result.all_macros, i):
            combinations_params.append(combination)

    return combinations_params
