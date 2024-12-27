package org.hyperledger.besu.precompiles;

public class Anonymous {
    private static native long nativeGas(final byte[] input);

    private static native byte nativeRun(final byte[] input);

    static {
        System.loadLibrary("precompiles");
    }

    public static long requiredGas(final byte[] input) {
        return nativeGas(input);
    }

    public static byte[] run(final byte[] input) {
        var output = new byte[32];
        output[31] = nativeRun(input);
        return output;
    }
}
