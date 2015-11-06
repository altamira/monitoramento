/*
 * Copyright 2014 the original author or authors.
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

package br.com.altamira.monitoramento.controller;

import java.util.Date;

/**
 * @author Alain Sahli
 * @param <T>
 */
public class DataWithTimestamp<T> {

    private final T data;

    private final String timestamp;

    /**
     *
     * @param data
     */
    public DataWithTimestamp(T data) {
        this(data, String.valueOf(new Date().getTime()));
    }

    /**
     *
     * @param data
     * @param timestamp
     */
    public DataWithTimestamp(T data, String timestamp) {
        this.timestamp = timestamp;
        this.data = data;
    }

    /**
     *
     * @return
     */
    public T getData() {
        return this.data;
    }

    /**
     *
     * @return
     */
    public String getTimestamp() {
        return this.timestamp;
    }
}
