package expirable.threads;

import java.util.Random;
import java.util.regex.Pattern;

public class OtpGenerator {
  private static final Random random = new Random(new Random().nextLong()
      + System.currentTimeMillis() + new Random(System.currentTimeMillis()).nextLong());

  private RandomNumberGenerator generator;

  public OtpGenerator() {

    final Pattern pattern = Pattern.compile("\\b(\\d+)\\1+\\b");

    generator = (digitCount, nonRepetitive) -> {
      if (digitCount < 1) {
        digitCount = 1;
      }

      final int lowerIntBound = 0;
      final int upperIntBound = 9;

      final StringBuilder sb = new StringBuilder();
      int lastGenerated = -1;
      int generated = -1;

      for (int i = 0; i < digitCount; i++) {

        generated = nonRepetitive ? getExcept(lastGenerated, lowerIntBound, upperIntBound)
            : generateRandom(lowerIntBound, upperIntBound);
        sb.append(String.valueOf(generated));
        if (nonRepetitive) {
          lastGenerated = generated;
        }
      }
      String result = sb.toString();

      return nonRepetitive
          ? (pattern.matcher(result).matches() ? generator.generate(digitCount, nonRepetitive)
              : result)
          : result;
    };
  }

  public String generateNumber(int digitLength) {
    return generator.generate(digitLength, false);
  }

  public String generateNonConsecutiveRepetitive(int digitLength) {
    return generator.generate(digitLength, true);
  }

  private int generateRandom(int lower, int upper) {
    final int range = upper - lower + 1;
    return random.nextInt(range) + lower;
  }

  private int getExcept(int toBeExcluded, int lower, int upper) {
    if (lower > toBeExcluded) {
      lower = toBeExcluded;
    }
    int generated = generateRandom(lower, upper);
    while (generated == toBeExcluded) {
      generated = generateRandom(lower, upper);
    }
    return generated;
  }
}
