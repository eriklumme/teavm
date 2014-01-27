/*
 *  Copyright 2013 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.optimization;

import java.util.Arrays;
import java.util.List;
import org.teavm.model.ClassHolder;
import org.teavm.model.ListableClassHolderSource;
import org.teavm.model.MethodHolder;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
public class ClassSetOptimizer {
    private List<MethodOptimization> optimizations = Arrays.<MethodOptimization>asList(
            new CommonSubexpressionElimination(), new UnusedVariableElimination());

    public void optimizeAll(ListableClassHolderSource classSource) {
        for (String className : classSource.getClassNames()) {
            ClassHolder cls = classSource.getClassHolder(className);
            for (MethodHolder method : cls.getMethods()) {
                if (method.getProgram() != null && method.getProgram().basicBlockCount() > 0) {
                    for (MethodOptimization optimization : optimizations) {
                        optimization.optimize(method);
                    }
                }
            }
        }
    }
}
