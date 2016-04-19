/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.website;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/image", name = "DynamicContentServlet")
public class DynamicContentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("image/svg+xml");
        String name = req.getParameter("name");
        if (name == null) {
            name = "";
        }
        resp.getWriter()
                .write("<?xml version='1.0' encoding='UTF-8' standalone='no'?>"
                        + "<svg  xmlns='http://www.w3.org/2000/svg' "
                        + "xmlns:xlink='http://www.w3.org/1999/xlink'>"
                        + "<rect x='10' y='10' height='100' width='100' "
                        + "style=' fill: #90C3D4'/>"
                        + "<text x='30' y='30' fill='red'>" + name + "</text>"
                        + "</svg>");
    }
}
