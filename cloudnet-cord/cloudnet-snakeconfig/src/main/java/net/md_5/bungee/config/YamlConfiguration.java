package net.md_5.bungee.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class YamlConfiguration extends ConfigurationProvider {

    private final ThreadLocal<Yaml> yaml = new ThreadLocal<Yaml>() {
        @Override
        protected Yaml initialValue()
        {
            Representer representer = new Representer() {
                {
                    representers.put(Configuration.class, new Represent() {
                        @Override
                        public Node representData(Object data)
                        {
                            return represent(((Configuration) data).self);
                        }
                    });
                }
            };

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            return new Yaml(new Constructor(), representer, options);
        }
    };

    @Override
    public void save(Configuration config, File file) throws IOException
    {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)))
        {
            save(config, writer);
        }
    }

    @Override
    public void save(Configuration config, Writer writer)
    {
        yaml.get().dump(config.self, writer);
    }

    @Override
    public Configuration load(File file) throws IOException
    {
        return load(file, null);
    }

    @Override
    public Configuration load(File file, Configuration defaults) throws IOException
    {
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)))
        {
            return load(reader, defaults);
        }
    }

    @Override
    public Configuration load(Reader reader)
    {
        return load(reader, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(Reader reader, Configuration defaults)
    {
        Map<String, Object> map = yaml.get().loadAs(reader, LinkedHashMap.class);
        if (map == null)
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration(map, defaults);
    }

    @Override
    public Configuration load(InputStream is)
    {
        return load(is, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(InputStream is, Configuration defaults)
    {
        Map<String, Object> map = yaml.get().loadAs(is, LinkedHashMap.class);
        if (map == null)
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration(map, defaults);
    }

    @Override
    public Configuration load(String string)
    {
        return load(string, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(String string, Configuration defaults)
    {
        Map<String, Object> map = yaml.get().loadAs(string, LinkedHashMap.class);
        if (map == null)
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration(map, defaults);
    }
}
