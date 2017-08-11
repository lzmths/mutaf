import sys
import shutil
import os
import json

from evaluation.evaluation_executor import EvaluationExecutor
from evaluation.combination_runner import CombinationRunner
from evaluation import diff_strategies, compile_strategies, combination_strategies
from impact_analysis.braz_analysis import Braz


PROGRAM_NAME = 'impact'


def check_dependencies():
    if shutil.which('gcc') is None:
        print('%s: gcc not found' % PROGRAM_NAME)
        sys.exit(1)

    if shutil.which('diff') is None:
        print('%s: diff not found' % PROGRAM_NAME)
        sys.exit(1)


def check_files(first_file, second_file):
    if not os.path.isfile(first_file):
        print("%s: cannot stat '%s': No such file" % (PROGRAM_NAME, first_file))
        sys.exit(1)

    if not os.path.isfile(second_file):
        print("%s: cannot stat '%s': No such file" % (PROGRAM_NAME, second_file))
        sys.exit(1)

    return first_file, second_file


def print_help():
    print('\nUsage:\n  %s <old file> <new file>' % PROGRAM_NAME)
    sys.exit(0)


def write_abstract(path):
    filename = path + '.json'
    log_dir = 'log'
    all_combinations = [list(combination) for combination in runner.combinations]

    abstract = {
        'oldFile': old_file,
        'newFile': new_file,
        'features': analysis_result.all_macros,
        'impactedFeatures': analysis_result.impacted_macros,
        'combinations': all_combinations,
        'totalCombinations': len(all_combinations),
        'enoughCombinations': result,
        'totalEnoughCombinations': len(result)
    }

    if os.path.isfile(filename):
        os.remove(filename)

    if not os.path.exists(log_dir):
        os.mkdir(log_dir)

    file = open(os.path.join(log_dir, filename), 'w')
    file.write(json.dumps(abstract, indent=2, sort_keys=True))


def format_output(results):
    for r in results:
        for feature in r:
            sys.stdout.write(feature + ' ')
        sys.stdout.write('\n')
    sys.stdout.flush()


if __name__ == "__main__":
    list_of_files = []

    if len(sys.argv) < 3:
        print_help()
    else:
        check_dependencies()
        old_file, new_file = check_files(sys.argv[-2], sys.argv[-1])

        combinations = []

        old_code = open(old_file, "rt").readlines()
        new_code = open(new_file, "rt").readlines()

        analysis_result = Braz(old_code, new_code).run(verbose=False)

        result_path = (old_file.split('/')[-1] + '-' + new_file.split('/')[-1]).replace('.c', '')

        evaluation_executor = EvaluationExecutor(
            compile_strategy=compile_strategies.compile_preprocessor_only,
            diff_strategy=diff_strategies.diff_preprocessor_out,
            path=result_path,
        )

        runner = CombinationRunner(
            analysis_result=analysis_result,
            evaluation_executor=evaluation_executor,
            combination_strategy=combination_strategies.impacted_feature
        )

        result = runner.run_and_clean([sys.argv[1], sys.argv[2]])

        write_abstract(result_path)
        format_output(result)
