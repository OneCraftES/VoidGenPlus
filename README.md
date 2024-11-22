THIS FORK IS UNTESTED AND MAY CONTAIN ERRORS OR BUGS - USE AT YOUR OWN RISK.

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

## Quick Start

### Basic Usage
Create a void world using MultiVerse-Core:
```bash
/mv create <worldname> <environment> -g VoidGenPlus
```

Where:
- `<worldname>` is your desired world name
- `<environment>` is one of: normal, nether, or end
- `-g VoidGenPlus` specifies our void generator

Examples:
```bash
# Create an overworld void
/mv create voidworld normal -g VoidGenPlus

# Create a nether void
/mv create nethervoid nether -g VoidGenPlus

# Create an end void
/mv create endvoid end -g VoidGenPlus
```

### Advanced Configuration
You can customize the world generation by adding settings:
```bash
# Create a world with custom layers
/mv create flatworld normal -g VoidGenPlus -s "layers:minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block"

# Create a nether world with custom layers
/mv create netherlayers nether -g VoidGenPlus -s "layers:minecraft:bedrock,5*minecraft:netherrack,minecraft:soul_sand"
```

## World Creation

When creating a world with Multiverse, use these world types:

- For normal worlds: `normal`
- For nether worlds: `nether`
- For end worlds: `end`

Examples:

```bash
# Create a normal void world
/mv create voidworld normal -g VoidGenPlus:126*minecraft:air,minecraft:bedrock;1;biome=minecraft:plains

# Create a nether void world
/mv create netherworld nether -g VoidGenPlus:126*minecraft:air,minecraft:bedrock;1;biome=minecraft:nether_wastes

# Create an end void world
/mv create endworld end -g VoidGenPlus:126*minecraft:air,minecraft:bedrock;1;biome=minecraft:end_highlands
```

The world type automatically sets the correct environment which affects:
- Sky color and fog
- Ambient lighting
- Special effects (like nether fog)
- Default world height limits

## Features

### Dimension Support
- Works in any dimension (Overworld, Nether, End)
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

## Environment Effects

Different environments (Overworld, Nether, End) have specific ambient effects:

### Nether Effects
To enable nether-specific effects like bedrock particles and ambient sounds:
```
noise: true
```

This will enable:
- Ambient bedrock particles
- Nether ambient sounds
- Other dimension-specific effects

Note: These effects only apply to newly created worlds or when changing settings. Existing chunks will keep their previous settings.

## Nether Portal Setup

When creating nether void worlds, you'll need to properly set up portal linking to avoid errors. There are two approaches:

### Using Multiverse-NetherPortals (Recommended)

1. Install the Multiverse-NetherPortals plugin
2. Link your worlds using the following commands:
```bash
# Link an overworld to a nether void world
/mvnp link normal_world nether_void_world

# Or allow all nether portals to link to your void nether
/mvnp set nether nether_void_world
```

### Manual Portal Creation
If you're not using Multiverse-NetherPortals, you'll need to:
1. Create both the overworld and nether void worlds
2. Manually build portals in both worlds
3. Use `/mvtp` to travel between worlds instead of portals

Note: Using nether portals without proper world linking can cause errors or crashes.

## ⚙️ Configuration

### Basic Settings
```json
{
  "biome": "PLAINS",            // Custom biome (optional)
  "bedrock": true,             // Place bedrock at minimum height
  "minHeight": -64,            // Min build height
  "maxHeight": 320,            // Max build height
  "layers": "minecraft:bedrock,minecraft:dirt,minecraft:grass_block"  // Custom layers
}
```

## 🔧 Technical Details
- Java Version: 21
- Build System: Gradle
- Key Dependencies:
  * Bukkit/Spigot API (1.21.3-R0.1-SNAPSHOT)
  * Apache Commons Lang3 (3.14.0)
  * Google Gson (2.10.1)

## 🌐 Supported Environments
- Minecraft versions: 1.8.8 to 1.21.3
- Dimensions: Overworld, Nether, End
- Configurable world generation parameters

## License
VoidGenPlus is licensed under the [GNU General Public License v3.0](https://github.com/xtkq-is-not-available/VoidGen/blob/master/LICENSE.md).
