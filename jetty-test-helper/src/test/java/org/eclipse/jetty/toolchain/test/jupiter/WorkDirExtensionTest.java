//
//  ========================================================================
//  Copyright (c) 1995-2020 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.toolchain.test.jupiter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing Junit Jupiter behavior with {@link WorkDir} and {@link WorkDirExtension}
 */
@ExtendWith({WorkDirExtension.class})
public class WorkDirExtensionTest
{
    public WorkDir fieldDir;
    private Object objA;

    @Test
    public void testWorkDir_AsField()
    {
        assertThat("testingDir", fieldDir, is(notNullValue()));
        assertThat("testingDir.getPath()", fieldDir.getPath(), is(notNullValue()));
    }

    @Test
    public void testWorkDir_AsParam(WorkDir dir)
    {
        assertThat("testingDir", dir, is(notNullValue()));
        assertThat("testingDir.getPath()", dir.getPath(), is(notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "bar", "a longer\nstring\r\bwith\tcontrol\0characters"})
    public void testWorkDir_WithParameterized(String val)
    {
        fieldDir.getPath();
    }

    @Test
    public void testWorkDir_TestInfo(TestInfo testInfo)
    {
        fieldDir.getPath();
    }

    private static Stream<Arguments> testValues()
    {
        return Stream.of(
            Arguments.of("", "", null),
            Arguments.of(":80", "", "80"),
            Arguments.of("host", "host", null)
        );
    }

    @ParameterizedTest
    @MethodSource("testValues")
    public void testWorkDir_WithParameterizedMethodSource(String a, String b, String c)
    {
        fieldDir.getPath();
    }

    // Leave this declared as List<Object[]> as this triggers a different codepath in junit5
    private static List<Object[]> objValues()
    {
        List<Object[]> ret = new ArrayList<>();

        HashMap<String, Object> map1 = new HashMap<>();
        ret.add(new Object[]{"{}", map1});

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("a", "foo");
        ret.add(new Object[]{"{'a': 'foo'}", map2});

        return ret;
    }

    @ParameterizedTest
    @MethodSource("objValues")
    public void testWorkDir_ObjectParameters(Object obj1, Object obj2)
    {
        assertNotNull(obj1);
        assertNotNull(obj2);
    }

    private static Stream<Arguments> wholeCharSpace()
    {
        List<Arguments> cases = new ArrayList<>();
        for (int i = 0; i < 128; i++)
        {
            cases.add(Arguments.of("dir-" + (char)i));
        }
        return cases.stream();
    }

    @ParameterizedTest(name="[{index}] {arguments}")
    @MethodSource("wholeCharSpace")
    public void testWorkDir_WholeCharspace(String path)
    {
        Path dir = fieldDir.getEmptyPathDir();
        assertTrue(Files.exists(dir), "Does [" + path + "] exist: " + dir);
        assertTrue(Files.isDirectory(dir), "Is [" + path + "] a directory: " + dir);
    }
}

