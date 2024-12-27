package org.hyperledger.besu.precompiles;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HexFormat;

public class AnemoiTest {
    @Test
    public void evalJive4_1() {
        final var inputData = "73808263b6b714840000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003644ea1f2fc768cf2749997d154f34d16beb50249a19fab3386705997c594629";
        final var inputBytes = HexFormat.of().parseHex(inputData);
        assert Arrays.equals(
                Anemoi.run(inputBytes),
                HexFormat.of().parseHex("1c8bb26902c2ef7f62ff9996f55df3d1711450c5d64c3472d331d190f1c258110000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void evalJive4_2() {
        final var inputData = "738082630000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003644ea1f2fc768cf2749997d154f34d16beb50249a19fab3386705997c594629";
        final var inputBytes = HexFormat.of().parseHex(inputData);
        assert Arrays.equals(
                Anemoi.run(inputBytes),
                HexFormat.of().parseHex("d162ff41b9d4b15a26e6de5359904ad0dd837faf9bd3ce32976b565bf5e170070000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void temp() {
        final var inputData = "738082636a53d9a7051342adb81e637883005bc399c40c32c5d9f5e12cf0112a4066f22cc6e831f5f3d6eecddfa763199a633c9ab49b851c7112df7b59b17b87473a7217dcefbd4f25049ddf3ff781f89d638c98ea657bd7f616b04c54122b4cac03550d3644ea1f2fc768cf2749997d154f34d16beb50249a19fab3386705997c594629";
        final var inputBytes = HexFormat.of().parseHex(inputData);
        assert Arrays.equals(
                Anemoi.run(inputBytes),
                HexFormat.of().parseHex("8d1a8129419fa9b4353c88ed8d5b17c910fd1fc0b80bc9f74f67792eb9234c2c0000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void variableLengthHash_1() {
        final var inputData = "47f3b0980000000000000000000000000000000000000000000000000000000137be295817412d25ba70f29bc3496dfdf0ff1a44754c307ff5cb5f749f6bc6b6a0c39f09";
        final var inputBytes = HexFormat.of().parseHex(inputData);
        assert Arrays.equals(
                Anemoi.run(inputBytes),
                HexFormat.of().parseHex("30f3960af88b339d878d38cf435e1d3758fdfe214597abd8fdbada2ecf46421e0000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void variableLengthHash_2() {
        final var inputData = "47f3b09800000000000000000000000000000000000000000000000000000062a32b155172b9cd2de6972234f8f8311127dda07164617f4a1e442478ddc2c0472450e514";
        final var inputBytes = HexFormat.of().parseHex(inputData);
        assert Arrays.equals(
                Anemoi.run(inputBytes),
                HexFormat.of().parseHex("d91004f1e1a065912b594119554a870df3506c98a7e07c89f38465d653e394240000000000000000000000000000000000000000000000000000000000000000"));
    }
}
