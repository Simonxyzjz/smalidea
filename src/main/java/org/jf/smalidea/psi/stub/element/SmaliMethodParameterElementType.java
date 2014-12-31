/*
 * Copyright 2014, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.smalidea.psi.stub.element;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.jf.smalidea.psi.impl.SmaliMethodParameter;
import org.jf.smalidea.psi.stub.SmaliMethodParameterStub;

import java.io.IOException;

public class SmaliMethodParameterElementType
        extends SmaliStubElementType<SmaliMethodParameterStub, SmaliMethodParameter> {
    public static final SmaliMethodParameterElementType INSTANCE = new SmaliMethodParameterElementType();

    private SmaliMethodParameterElementType() {
        super("METHOD_PARAMETER");
    }

    @NotNull @Override public String getExternalId() {
        return "smali.method_parameter";
    }

    @Override public SmaliMethodParameter createPsi(@NotNull ASTNode node) {
        return new SmaliMethodParameter(node);
    }

    @Override public SmaliMethodParameter createPsi(@NotNull SmaliMethodParameterStub stub) {
        return new SmaliMethodParameter(stub);
    }

    @Override public SmaliMethodParameterStub createStub(@NotNull SmaliMethodParameter psi, StubElement parentStub) {
        return new SmaliMethodParameterStub(parentStub, psi.getType().getCanonicalText(), psi.getName());
    }

    @Override
    public void serialize(@NotNull SmaliMethodParameterStub stub, @NotNull StubOutputStream dataStream)
                          throws IOException {
        dataStream.writeName(stub.getType());
        dataStream.writeName(stub.getName());
    }

    @NotNull @Override
    public SmaliMethodParameterStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub)
            throws IOException {
        String type = dataStream.readName().getString();
        StringRef nameRef = dataStream.readName();
        String name = nameRef==null ? null : nameRef.getString();

        return new SmaliMethodParameterStub(parentStub, type, name);
    }

    @Override public void indexStub(@NotNull SmaliMethodParameterStub stub, @NotNull IndexSink sink) {
    }
}