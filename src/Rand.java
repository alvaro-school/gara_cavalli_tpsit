public class Rand {
    // xoroshiro256** state
    private long[] state = {0,0,0,0};

    // used for init by the constructor
    private static long mix(long z) {
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    public Rand(long seed) {
        long smState = seed;

        smState += 0x9E3779B97F4A7C15L;
        state[0] = mix(smState);

        smState += 0x9E3779B97F4A7C15L;
        state[1] = mix(smState);

        smState += 0x9E3779B97F4A7C15L;
        state[2] = mix(smState);

        smState += 0x9E3779B97F4A7C15L;
        state[3] = mix(smState);
    }

    private static long rotl(long a, long b) {
        return (a << b) | (a >>> (64 - b));
    }

    public long next() {
        long result = rotl(state[1] * 5, 7) * 9;
        long t = state[1] << 17;

        state[2] = state[2] ^ state[0];
        state[3] = state[3] ^ state[1];
        state[1] = state[1] ^ state[2];
        state[0] = state[0] ^ state[3];

        state[2] = state[2] ^ t;
        state[3] = rotl(state[3], 45);

        return result;
    }
}
