<html>
<head>
<style>
img {
    display: block;
    margin-left: auto;
    margin-right: auto;
}
</style>

<body>
<div style="height: 100%; width: 100%; position: absolute; z-index: -1; top: 0; left: 0">
    <img src="bird.png" style="height: 100%"/>
</div>
<%@page import="java.io.File" %>
<%@page import="java.io.FileInputStream" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collections" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.jar.Manifest" %>
<%
String flowComponentsDemoName = "demo-flow-components";

Map<String, List<String>> stableVersions = new HashMap<>();
Map<String, List<String>> snapshotVersions = new HashMap<>();

// Java 1.7
File[] files = new File("/home/dev/tomcat/webapps/").listFiles();
if (files != null) {
    for (File f : files) {
        if (f.isDirectory()) {
            String nameAndVersion = f.getName();
            if (nameAndVersion.startsWith("demo-")) {
                if (nameAndVersion.startsWith(flowComponentsDemoName)) {
                    continue;
                }

                File manifest = new File(f, "META-INF/MANIFEST.MF");
                if (manifest.isFile()) {
                    Manifest mf = new Manifest(new FileInputStream(manifest));
                    String title = mf.getMainAttributes().getValue("Implementation-Title");
                    if (title != null) {
                        String version = mf.getMainAttributes().getValue("Implementation-Version");
                        if (version == null) {
                            int versionStarts = nameAndVersion.lastIndexOf("-");
                            if (nameAndVersion.endsWith("-SNAPSHOT")) {
                                versionStarts = nameAndVersion.lastIndexOf("-", versionStarts - 1);
                            }
                            version = nameAndVersion.substring(versionStarts + 1);
                        }
                        String name = nameAndVersion.substring(0, nameAndVersion.indexOf(version) - 1);
                        List<String> names;
                        String sourceLink;

                        if (version.endsWith("-SNAPSHOT")) {
                            names = snapshotVersions.get(version);
                            if (names == null) {
                                names = new ArrayList<>();
                                snapshotVersions.put(version, names);
                            }
                            sourceLink = "https://github.com/vaadin/flow-demo/tree/master/" + name;
                        } else {
                            names = stableVersions.get(version);
                            if (names == null) {
                                names = new ArrayList<>();
                                stableVersions.put(version, names);
                            }
                            sourceLink = "https://github.com/vaadin/flow-demo/tree/" + version + "/" + name;
                        }
                        names.add("<li><a href='" + nameAndVersion + "'>" + title + "</a> (<a href='" + sourceLink + "'>sources</a>)</li>");
                    }
                }
            }
        }
    }
}

List<String> sortedStableVersions = new ArrayList<>(stableVersions.keySet());
if (!sortedStableVersions.isEmpty()) {
  Collections.sort(sortedStableVersions);
  String latestStable = sortedStableVersions.get(sortedStableVersions.size() - 1);
  out.println("<b>"+latestStable+"</b>");
  out.println("<ul>");

  List<String> demos = stableVersions.get(latestStable);
  Collections.sort(demos);
  for (String link : demos) {
    out.println(link);
  }
  out.println("</ul>");
}

List<String> sortedSnapshotVersions = new ArrayList<>(snapshotVersions.keySet());
if (!sortedSnapshotVersions.isEmpty()) {
  Collections.sort(sortedSnapshotVersions);
  String latestSnapshot = sortedSnapshotVersions.get(sortedSnapshotVersions.size() - 1);
  out.println("<b>"+latestSnapshot+"</b>");
  out.println("<ul>");

  List<String> demos = snapshotVersions.get(latestSnapshot);
  Collections.sort(demos);
  for (String link : demos) {
    out.println(link);
  }
  out.println("</ul>");
}


out.println("<b>Flow components demo</b>");
out.println("<ul>");
out.println("<li><a href='components'>Flow components demo</a> (<a href='https://github.com/vaadin/flow/tree/master/flow-components-parent/demo-flow-components'>sources</a>)</li>");
out.println("</ul>");
%>
</body>
</html>
