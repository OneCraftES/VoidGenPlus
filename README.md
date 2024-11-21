<p align="center">
    <img src="docs/assets/Logo.svg" width=50%>
    <br>
    <a href="https://discord.gg/Q7yj32FMFh"><img src="https://discordapp.com/api/guilds/681986370214166548/widget.png?style=shield"></a>   
    <a href="https://github.com/xtkq-is-not-available/VoidGen/releases/latest"><img src="https://img.shields.io/github/v/release/xtkq-is-not-available/VoidGen?label=release&color=success"></a>
    <a href="https://github.com/xtkq-is-not-available/VoidGen/releases/latest"><img alt="GitHub all releases" src="https://img.shields.io/github/downloads/xtkq-is-not-available/VoidGen/total"></a>    
    <a href="https://github.com/xtkq-is-not-available/VoidGen/blob/master/LICENSE.md"><img src="https://img.shields.io/github/license/xtkq-is-not-available/VoidGen?label=license&color=success"></a>
</p>

# VoidGen

VoidGen is a Minecraft plugin that enables server owners to create countless custom void worlds with a powerful and
lightweight generator system. The plugin is made to handle all aspects of how you create void worlds. If you find any bug or have crashed because of the plugin, please report it at
my [official Discord server](https://discord.gg/Q7yj32FMFh).

## How To

Find out how to setup and use the plugin on the [Tutorial page](docs/tutorial.md).

<!---
## FAQ

Find answers to frequently asked questions on the [FAQ page](docs/faq.md).
--->
## License

VoidGen is licensed under
the [GNU General Public License v3.0](https://github.com/xtkq-is-not-available/VoidGen/blob/master/LICENSE.md). 

# VoidGenPlus

VoidGenPlus is a powerful fork of the VoidGen plugin that enhances void world generation with advanced features and full dimension support. The plugin enables server owners to create customized void worlds across all Minecraft dimensions (Overworld, Nether, and End) with precise control over generation settings.

## New Features in VoidGenPlus

### Full Dimension Support
- Create void worlds in any dimension (Overworld, Nether, End)
- Dimension-specific default biomes
- Proper height limits for each dimension
- Environment-aware generation settings

### Enhanced Configuration
- Per-dimension height limits
- Customizable build ranges
- Environment-specific biome settings
- Flexible generation parameters

### Build Height Control
- Configurable minimum and maximum heights per dimension
- Full build range support (-64 to 320)
- Dimension-specific default heights

### 🏗️ Custom Layer Generation
- Support for Minecraft's superflat layer format
- Define custom world layers using simple strings
- Mix and match with other generation settings
- Compatible with all dimensions

## 🚀 Quick Start

Create a void world with specific environment:
```
/mv create <worldname> VoidGenPlus:<environment>;<settings>
```

Examples:
```bash
# Create a normal void world
/mv create voidworld VoidGenPlus:normal

# Create a nether void world
/mv create voidnether VoidGenPlus:nether

# Create an end void world
/mv create voidend VoidGenPlus:the_end

# Create a world with custom layers
/mv create flatworld VoidGenPlus:normal;{"layers":"minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block"}

# Create a nether world with custom layers
/mv create netherlayers VoidGenPlus:nether;{"layers":"minecraft:bedrock,5*minecraft:netherrack,minecraft:soul_sand"}
```

## ⚙️ Configuration

### Basic Settings
```json
{
  "biome": "PLAINS",            // Custom biome (optional)
  "bedrock": true,             // Place bedrock at minimum height
  "minHeight": -64,            // Overworld min height
  "maxHeight": 320,            // Overworld max height
  "netherMinHeight": -64,      // Nether min height
  "netherMaxHeight": 320,      // Nether max height
  "endMinHeight": -64,         // End min height
  "endMaxHeight": 320          // End max height
}
```

### Layer Configuration
You can specify layers using Minecraft's superflat format:
```json
{
  "layers": "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block"
}
```

Layer format:
- Basic block: `minecraft:material`
- Multiple layers: `count*minecraft:material`
- Separate layers with commas
- Examples:
  - `minecraft:bedrock` - Single bedrock layer
  - `3*minecraft:stone` - Three stone layers
  - `minecraft:bedrock,5*minecraft:stone,minecraft:grass_block` - Multiple different layers

Common layer combinations:
```json
// Classic superflat
{
  "layers": "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block"
}

// Desert flat
{
  "layers": "minecraft:bedrock,3*minecraft:sandstone,minecraft:sand"
}

// Nether layers
{
  "layers": "minecraft:bedrock,4*minecraft:netherrack,minecraft:soul_sand"
}

// End platform
{
  "layers": "minecraft:bedrock,minecraft:obsidian,minecraft:end_stone"
}
```

Default Biomes:
- Overworld: PLAINS
- Nether: NETHER_WASTES
- End: THE_END
