def append_if_not_in(to_list, from_list):
    for item in from_list:
        if item not in to_list:
            to_list.append(item)
    return to_list
