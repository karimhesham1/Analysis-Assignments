def compute_highest_scoring_alignment(x, y, scoring_matrix):
    n, m = len(x), len(y)
    dp_matrix = [[0] * (m + 1) for _ in range(n + 1)]

    for i in range(1, n + 1):
        for j in range(1, m + 1):
            match_score = dp_matrix[i - 1][j - 1] + scoring_matrix[x[i - 1]][y[j - 1]]
            gap_x_score = dp_matrix[i - 1][j] + scoring_matrix[x[i - 1]]['-']
            gap_y_score = dp_matrix[i][j - 1] + scoring_matrix['-'][y[j - 1]]

            dp_matrix[i][j] = max(match_score, gap_x_score, gap_y_score)

    alignment_x, alignment_y = '', ''
    i, j = n, m
    while i > 0 or j > 0:
        if i > 0 and j > 0 and dp_matrix[i][j] == dp_matrix[i - 1][j - 1] + scoring_matrix[x[i - 1]][y[j - 1]]:
            alignment_x, alignment_y, i, j = x[i - 1] + alignment_x, y[j - 1] + alignment_y, i - 1, j - 1
        elif i > 0 and dp_matrix[i][j] == dp_matrix[i - 1][j] + scoring_matrix[x[i - 1]]['-']:
            alignment_x, alignment_y, i = x[i - 1] + alignment_x, '-' + alignment_y, i - 1
        else:
            alignment_x, alignment_y, j = '-' + alignment_x, y[j - 1] + alignment_y, j - 1

    return alignment_x, alignment_y

seq_x, seq_y = "ATGCC", "TACGCA"

scoring_mat = {
    'A': {'A': 1, 'G': -0.8, 'T': -0.2, 'C': -2.3, '-': -0.6},
    'G': {'A': -0.8, 'G': 1, 'T': -1.1, 'C': -0.7, '-': -1.5},
    'T': {'A': -0.2, 'G': -1.1, 'T': 1, 'C': -0.5, '-': -0.9},
    'C': {'A': -2.3, 'G': -0.7, 'T': -0.5, 'C': 1, '-': -1},
    '-': {'A': -0.6, 'G': -1.5, 'T': -0.9, 'C': -1, '-': float('inf')}
}

result_alignment = compute_highest_scoring_alignment(seq_x, seq_y, scoring_mat)

print("Optimal Alignment for Sequences:")
print(result_alignment[0])
print(result_alignment[1])
