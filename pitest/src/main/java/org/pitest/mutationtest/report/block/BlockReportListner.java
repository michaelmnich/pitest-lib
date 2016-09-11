package org.pitest.mutationtest.report.block;

import org.pitest.mutationtest.ClassMutationResults;
import org.pitest.mutationtest.MutationResultListener;

/**
 * Created by gosc on 10.09.2016.
 */
public class BlockReportListner implements MutationResultListener {
    @Override
    public void runStart() {

    }

    @Override
    public void handleMutationResult(ClassMutationResults results) {
        System.out.println("Handluje resoulty tlalalala lalalalallalalallalalalallalallalalal");
    }

    @Override
    public void runEnd() {

    }
}
