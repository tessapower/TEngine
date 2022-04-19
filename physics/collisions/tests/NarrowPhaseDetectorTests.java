package physics.collisions.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import physics.PhysicsBody;
import physics.collisions.detection.NarrowPhaseDetector;
import physics.collisions.events.CollisionEvent;
import physics.collisions.shapes.CollisionRect;
import physics.kinematics.Vector;
import physics.kinematics.Velocity;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <code>NarrowPhaseDetector</code> tests involve testing the <code>CollisionEvent</code> that is produced by two
 * given <code>PhysicsBody</code>s, one that is moving and one that is static. We expect that the impact vector
 * indicates the path the moving <code>PhysicsBody</code> took up until colliding with the static
 * <code>PhysicsBody</code>, and that the impact side indicates the side of the moving <code>PhysicsBody</code>
 * collided with the static <code>PhysicsBody</code>.
 * <br><br>
 * <img src="../../../docs/impact_side.png" />
 */
class NarrowPhaseDetectorTests {

    /**
     * <img src="../../../docs/collision_rect_rect_+x_0y.png" />
     */
    @Test
    @DisplayName("Rect vs. Rect (+, 0)")
    void rectRect_PosX_ZeroY() {
        PhysicsBody staticBody = new PhysicsBody(
                new Velocity(),
                true,
                new CollisionRect(
                        new Point(100, 10),
                        new Dimension(100, 100)
                ),
                true
        );

        PhysicsBody movingBody = new PhysicsBody(
                new Velocity(15, 0),
                false,
                new CollisionRect(
                        new Point(35, 0),
                        new Dimension(75, 50)
                        ),
                true);

        CollisionEvent expected = new CollisionEvent(movingBody, staticBody);
        expected.setImpactVector(new Vector());
        expected.setImpactSide(new Point(1, 0));

        CollisionEvent actual = NarrowPhaseDetector.detect(movingBody, staticBody);
        assertEquals(actual, expected);
    }

    /**
     * <img src="../../../docs/collision_rect_rect_-x_0y.png" />
     */
    @Test
    @DisplayName("Rect vs. Rect (-, 0)")
    void rectRect_NegX_ZeroY()  {

    }

    // pure y velocity
        // positive y
        // negative y

    // x == y -> 45 degree angles

    // +x, +y
        //

    // +x, -y

    // -x, +y

    // -x, -y

    /**
     * <img src="../../../docs/collision_rect_rect_-x_-y_right.png" />
     */
    @Test
    @DisplayName("Rect vs. Rect (-, -) Impact Right")
    void rectRect_NegX_NegY_Right() {

    }

}
