/*
 * Copyright 2011 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.concurrentrichtext.shared;

import com.google.gwt.user.client.Timer;

public abstract class AdaptativeTimer {

    private static final int delayIncrement = 500;
    private Timer timer;
    private int minDelay;
    private int maxDelay;
    private int currentDelay;

    /**
     * @param minDelay Milliseconds. Timer will default to this delay
     * @param maxDelay Milliseconds
     */
    public AdaptativeTimer(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.currentDelay = minDelay;

        this.timer = new Timer() {
            @Override
            public void run() {
                timerTick();
            }
        };
    }

    public abstract void timerTick();

    public void start() {
        setTimer();
    }

    public void cancel() {
        timer.cancel();
    }

    public void quicken() {
        if (currentDelay > minDelay) {
            currentDelay -= delayIncrement;
            if (currentDelay < minDelay) {
                currentDelay = minDelay;
            }
            setTimer();
        }
    }

    public void delay() {
        if (currentDelay < maxDelay) {
            currentDelay += delayIncrement;
            if (currentDelay > maxDelay) {
                currentDelay = maxDelay;
            }
            setTimer();
        }

    }

    private void setTimer() {
        timer.scheduleRepeating(currentDelay);
    }
}
