package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;

import static org.assertj.core.api.Assertions.assertThat;
import static zest.BestTimeToBuyAndSellStock.maxProfit;


public class BestTimeToBuyAndSellStockTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void checkValidCases(int [] prices, int expectedProfit) {
        assertThat(maxProfit(prices)).isEqualTo(expectedProfit);
    }

    @Test
    void checkNegativePrice() {
        assertThatThrownBy(
                ()->{
                    maxProfit(new int[]{-1});
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void checkNoPrice() {
        assertThatThrownBy(
                ()->{
                    maxProfit(new int[]{});
                }
        ).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                ()->{
                    maxProfit(null);
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkUpperPriceArrayBound() {
        int size = (int) Math.pow(10, 5);
        int[] enoughPrices = new int[size];
        Arrays.fill(enoughPrices, 0);
        enoughPrices[size-1]  = 1;
        assertThat(maxProfit(enoughPrices)).isEqualTo(1);

        int[] tooManyPrices = new int[size+1];
        Arrays.fill(enoughPrices, 0);
        tooManyPrices[size]  = 1;
        assertThatThrownBy(
                ()->{
                    maxProfit(tooManyPrices);
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkUpperPriceBound() {
        int limitPrice = (int) Math.pow(10, 4);
        assertThat(maxProfit(new int[] {0, limitPrice})).isEqualTo(limitPrice);
        int unlimitedPrice = limitPrice + 1;
        assertThatThrownBy(
                ()->{
                    maxProfit(new int[] {0, unlimitedPrice});
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                of(new int[]{1}, 0),
                of(new int[]{1, 5}, 4),
                of(new int[]{5, 1}, 0),
                of(new int[]{1, 5, 5}, 4),
                of(new int[]{2, 5, 2, 5}, 3),
                of(new int[]{5, 4, 3, 1, 2}, 1)
        );
    }
}