package jahmm.jadetree;

import java.util.logging.Logger;
import junit.framework.Assert;
import jutils.testing.AssertExtensions;
import jutlis.algebra.Predicate;
import jutlis.algebra.predicates.GreaterThanOrEqualToPredicate;
import jutlis.algebra.predicates.LessThanPredicate;
import jutlis.lists.ListArray;
import org.junit.Test;

/**
 *
 * @author kommusoft
 */
public class PredicateDecisionNodeTest {

    private static final Logger LOG = Logger.getLogger(PredicateDecisionNodeTest.class.getName());
    Predicate<Integer> pred1;
    Predicate<String> pred2;

    public PredicateDecisionNodeTest() {
        pred1 = new LessThanPredicate<>(4);
        pred2 = new GreaterThanOrEqualToPredicate<>("kommusoft");
    }

    /**
     * Test of toString method, of class PredicateDecisionNode.
     */
    @Test
    public void testToString() {
        PredicateDecisionNode<Integer> pdn1 = new PredicateDecisionNode<>(null, pred1);
        Assert.assertEquals("< 4", pdn1.toString());
        PredicateDecisionNode<String> pdn2 = new PredicateDecisionNode<>(null, pred2);
        Assert.assertEquals(">= kommusoft", pdn2.toString());
    }

    /**
     * Test of test method, of class PredicateDecisionNode.
     */
    @Test
    public void testTest() {
        PredicateDecisionNode<Integer> pdn1 = new PredicateDecisionNode<>(null, pred1);
        Assert.assertTrue(pdn1.test(0x02));
        Assert.assertTrue(pdn1.test(-0x02));
        Assert.assertTrue(pdn1.test(0x00));
        Assert.assertFalse(pdn1.test(0x04));
        Assert.assertTrue(pdn1.test(Integer.MIN_VALUE));
        Assert.assertFalse(pdn1.test(0x05));
        Assert.assertFalse(pdn1.test(0x07));
        Assert.assertFalse(pdn1.test(Integer.MAX_VALUE));
        PredicateDecisionNode<String> pdn2 = new PredicateDecisionNode<>(null, pred2);
        Assert.assertFalse(pdn2.test(null));
        Assert.assertTrue(pdn2.test("zzz"));
        Assert.assertTrue(pdn2.test("kommusoft"));
        Assert.assertFalse(pdn2.test("ape"));
        Assert.assertTrue(pdn2.test("monkey business"));
        Assert.assertFalse(pdn2.test("kommu"));
        Assert.assertFalse(pdn2.test("kola"));
        Assert.assertTrue(pdn2.test("kommusoftz"));
    }

    /**
     * Test of getPredicate method, of class PredicateDecisionNode.
     */
    @Test
    public void testGetPredicate() {
        PredicateDecisionNode<Integer> pdn1 = new PredicateDecisionNode<>(null, pred1);
        Assert.assertEquals(pred1, pdn1.getPredicate());
        PredicateDecisionNode<String> pdn2 = new PredicateDecisionNode<>(null, pred2);
        Assert.assertEquals(pred2, pdn2.getPredicate());
    }

    /**
     * Test of test method, of class PredicateDecisionNode.
     */
    @Test
    public void testInsert() {
        Integer[] vals1 = new Integer[]{0x02, -0x02, 0x00, 0x04, Integer.MIN_VALUE, 0x05, 0x07, Integer.MAX_VALUE};
        ListArray<Integer> vals1t = new ListArray<>(new Integer[]{0x02, -0x02, 0x00, Integer.MIN_VALUE});
        ListArray<Integer> vals1f = new ListArray<>(new Integer[]{0x04, 0x05, 0x07, Integer.MAX_VALUE});
        PredicateDecisionNode<Integer> pdn1 = new PredicateDecisionNode<>(null, pred1, vals1);
        AssertExtensions.assertEqualsOrdered(vals1t, pdn1.getTrueNode());
        AssertExtensions.assertEqualsOrdered(vals1f, pdn1.getFalseNode());

        String[] vals2 = new String[]{null, "zzz", "kommusoft", "ape", "monkey business", "kommu", "kola", "kommusoftz"};
        ListArray<String> vals2t = new ListArray<>(new String[]{"zzz","kommusoft","monkey business","kommusoftz"});
        ListArray<String> vals2f = new ListArray<>(new String[]{null,"ape","kommu","kola"});
        PredicateDecisionNode<String> pdn2 = new PredicateDecisionNode<>(null, pred2, vals2);
        AssertExtensions.assertEqualsOrdered(vals2t, pdn2.getTrueNode());
        AssertExtensions.assertEqualsOrdered(vals2f, pdn2.getFalseNode());
    }

}
