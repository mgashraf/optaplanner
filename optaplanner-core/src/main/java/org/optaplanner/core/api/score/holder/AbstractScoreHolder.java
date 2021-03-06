/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.api.score.holder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.drools.core.common.AgendaItem;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.rule.Match;
import org.kie.api.runtime.rule.RuleContext;
import org.kie.api.runtime.rule.RuleRuntime;
import org.kie.internal.event.rule.ActivationUnMatchListener;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;

/**
 * Abstract superclass for {@link ScoreHolder}.
 */
public abstract class AbstractScoreHolder implements ScoreHolder, Serializable {

    protected final boolean constraintMatchEnabled;
    protected final Map<String, ConstraintMatchTotal> constraintMatchTotalMap;
    protected final Map<Object, Indictment> indictmentMap;
    protected final Score zeroScore;

    protected AbstractScoreHolder(boolean constraintMatchEnabled, Score zeroScore) {
        this.constraintMatchEnabled = constraintMatchEnabled;
        // TODO Can we set the initial capacity of this map more accurately? For example: number of rules
        constraintMatchTotalMap = constraintMatchEnabled ? new LinkedHashMap<>() : null;
        // TODO Can we set the initial capacity of this map more accurately by using entitySize?
        indictmentMap = constraintMatchEnabled ? new LinkedHashMap<>() : null;
        this.zeroScore = zeroScore;
    }

    @Override
    public boolean isConstraintMatchEnabled() {
        return constraintMatchEnabled;
    }

    @Override
    public Collection<ConstraintMatchTotal> getConstraintMatchTotals() {
        if (!isConstraintMatchEnabled()) {
            throw new IllegalStateException("When constraintMatchEnabled (" + isConstraintMatchEnabled()
                    + ") is disabled in the constructor, this method should not be called.");
        }
        return constraintMatchTotalMap.values();
    }

    @Override
    public Map<Object, Indictment> getIndictmentMap() {
        if (!isConstraintMatchEnabled()) {
            throw new IllegalStateException("When constraintMatchEnabled (" + isConstraintMatchEnabled()
                    + ") is disabled in the constructor, this method should not be called.");
        }
        return indictmentMap;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    protected void registerConstraintMatch(RuleContext kcontext,
            final Runnable constraintUndoListener, Supplier<Score> scoreSupplier) {
        AgendaItem<?> agendaItem = (AgendaItem) kcontext.getMatch();
        ActivationUnMatchListener activationUnMatchListener = agendaItem.getActivationUnMatchListener();
        ConstraintActivationUnMatchListener constraintActivationUnMatchListener;
        if (activationUnMatchListener == null) {
            constraintActivationUnMatchListener = new ConstraintActivationUnMatchListener(constraintUndoListener);
            if (constraintMatchEnabled) {
                // Not needed in fast code: Add ConstraintMatch
                constraintActivationUnMatchListener.constraintMatchTotal = findConstraintMatchTotal(kcontext);
            }
            agendaItem.setActivationUnMatchListener(constraintActivationUnMatchListener);
        } else {
            constraintActivationUnMatchListener = (ConstraintActivationUnMatchListener) activationUnMatchListener;
            constraintActivationUnMatchListener.overwriteMatch(constraintUndoListener);
        }
        if (constraintMatchEnabled) {
            List<Object> justificationList = extractJustificationList(kcontext);
            // Not needed in fast code: Add ConstraintMatch
            ConstraintMatch constraintMatch = constraintActivationUnMatchListener.constraintMatchTotal
                    .addConstraintMatch(justificationList, scoreSupplier.get());
            List<Indictment> indictmentList = new ArrayList<>(justificationList.size());
            for (Object justification : justificationList) {
                Indictment indictment = indictmentMap.computeIfAbsent(justification,
                        k -> new Indictment(justification, zeroScore));
                boolean added = indictment.addConstraintMatch(constraintMatch);
                if (added) {
                    indictmentList.add(indictment);
                }
            }
            constraintActivationUnMatchListener.constraintMatch = constraintMatch;
            constraintActivationUnMatchListener.indictmentList = indictmentList;
        }
    }

    private ConstraintMatchTotal findConstraintMatchTotal(RuleContext kcontext) {
        Rule rule = kcontext.getRule();
        String constraintPackage = rule.getPackageName();
        String constraintName = rule.getName();
        String constraintId = constraintPackage + "/" + constraintName;
        return constraintMatchTotalMap.computeIfAbsent(constraintId,
                k -> new ConstraintMatchTotal(constraintPackage, constraintName, zeroScore));
    }

    protected List<Object> extractJustificationList(RuleContext kcontext) {
        // Unlike kcontext.getMatch().getObjects(), this includes the matches of accumulate and exists
        return ((org.drools.core.spi.Activation) kcontext.getMatch()).getObjectsDeep();
    }

    private class ConstraintActivationUnMatchListener implements ActivationUnMatchListener {

        private Runnable constraintUndoListener;

        private ConstraintMatchTotal constraintMatchTotal;
        private List<Indictment> indictmentList;
        private ConstraintMatch constraintMatch;

        public ConstraintActivationUnMatchListener(Runnable constraintUndoListener) {
            this.constraintUndoListener = constraintUndoListener;
        }

        @Override
        public final void unMatch(RuleRuntime ruleRuntime, Match match) {
            unMatch();
            constraintUndoListener = null;
        }

        public void overwriteMatch(Runnable constraintUndoListener) {
            if (this.constraintUndoListener != null) {
                unMatch();
            }
            this.constraintUndoListener = constraintUndoListener;
        }

        public final void unMatch() {
            constraintUndoListener.run();
            if (constraintMatchEnabled) {
                // Not needed in fast code: Remove ConstraintMatch
                constraintMatchTotal.removeConstraintMatch(constraintMatch);
                for (Indictment indictment : indictmentList) {
                    indictment.removeConstraintMatch(constraintMatch);
                    if (indictment.getConstraintMatchSet().isEmpty()) {
                        indictmentMap.remove(indictment.getJustification());
                    }
                }
            }
        }

    }

}
