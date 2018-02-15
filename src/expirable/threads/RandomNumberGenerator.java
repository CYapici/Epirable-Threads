package expirable.threads;

@FunctionalInterface
public interface RandomNumberGenerator {
  String generate(int length, Boolean nonRepetitive);
}
