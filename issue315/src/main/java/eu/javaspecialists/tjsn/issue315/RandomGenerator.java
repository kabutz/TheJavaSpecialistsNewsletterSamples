/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.javaspecialists.tjsn.issue315;

import java.util.stream.*;

public interface RandomGenerator {
    /**
     * The {@link StreamableGenerator} interface augments the
     * {@link RandomGenerator} interface to provide methods that
     * return streams of {@link RandomGenerator} objects.
     * Ideally, such a stream of objects would have the property
     * that the behavior of each object is statistically
     * independent of all the others. In practice, one may have
     * to settle for some approximation to this property.
     */
    interface StreamableGenerator extends RandomGenerator {
        Stream<RandomGenerator> rngs();
    }

    /**
     * This interface is designed to provide a common protocol
     * for objects that generate sequences of pseudorandom values
     * and can be <i>split</i> into two objects (the original one
     * and a new one) each of which obey that same protocol (and
     * therefore can be recursively split indefinitely).
     */
    interface SplittableGenerator extends StreamableGenerator {
        SplittableGenerator split();
    }

    /**
     * This interface is designed to provide a common protocol
     * for objects that generate pseudorandom values and can
     * easily <i>jump</i> forward, by a moderate amount (ex.
     * 2<sup>64</sup>) to a distant point in the state cycle.
     */
    interface JumpableGenerator extends StreamableGenerator {
        void jump();
        double jumpDistance();
        RandomGenerator copyAndJump();
    }

    /**
     * This interface is designed to provide a common protocol
     * for objects that generate sequences of pseudorandom values
     * and can easily not only jump but also <i>leap</i> forward,
     * by a large amount (ex. 2<sup>128</sup>), to a very distant
     * point in the state cycle.
     */
    interface LeapableGenerator extends JumpableGenerator {
        void leap();
        double leapDistance();
        JumpableGenerator copyAndLeap();
    }

    /**
     * This interface is designed to provide a common protocol
     * for objects that generate sequences of pseudorandom values
     * and can easily <i>jump</i> forward, by an arbitrary
     * amount, to a distant point in the state cycle.
     */
    interface ArbitrarilyJumpableGenerator extends LeapableGenerator {
        void jumpPowerOfTwo(int logDistance);
        void jump(double distance);
        ArbitrarilyJumpableGenerator copyAndJump(double distance);
    }
}
