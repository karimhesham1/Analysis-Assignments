import time
import matplotlib.pyplot as plt

def power_iterative_2(x, n):
    result = 1
    while n > 0:
        result *= x
        n -= 1
    return result

def power_divide_conquer(x, n):
    if n == 0:
        return 1
    elif n % 2 == 0:
        half_pow = power_divide_conquer(x, n // 2) 
        time.sleep(0.5)
        return half_pow * half_pow
    else:
        half_pow = power_divide_conquer(x, (n - 1) // 2)
        time.sleep(0.5)
        return half_pow * half_pow * x

# Example usage:
x = 2  # Base number

# Exponents ranging from 10^0 to 10^6
exponents = [10**i for i in range(7)]

iterative_times = []
divide_conquer_times = []

for n in exponents:
    # execution time for the iterative method
    start_time = time.time()
    power_iterative_2(x, n)
    end_time = time.time()
    iterative_times.append(end_time - start_time)

    # Measure execution time for the divide-and-conquer method
    start_time = time.time()
    power_divide_conquer(x, n)
    end_time = time.time()
    divide_conquer_times.append(end_time - start_time)
    print(end_time - start_time)

# Plot the results
plt.figure(figsize=(10, 5))
plt.plot(exponents, iterative_times, label='Iterative')
plt.plot(exponents, divide_conquer_times, label='Divide and Conquer')
plt.xscale('log') 
plt.yscale('log')  
plt.xlabel('Exponent (n)')
plt.ylabel('Execution Time (seconds)')
plt.xticks(exponents, [f'10^{i}' for i in range(7)])  
plt.legend()
plt.title('Execution Time for Power Calculation Methods')
plt.grid()
plt.show()
