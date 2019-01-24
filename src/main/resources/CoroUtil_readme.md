CoroAI
======

Main Uses: Entity AI implementations and threaded pathfinding, originally based off of the Minecraft pathfinding code.

Features:
---------

- "Unlimited" pathfind range, tries smaller angles of approach when destination is outside set max full pathfind range
- Interface with callback for custom mobs
- Injects path data if used on vanilla mobs if their default targetting system isnt actively overriding it
- Contains various fixes, eg: fences, doors, logic blocks are dealt with properly now
- Designed to run on a thread
- Uses a queue system to manage pathfind requests

- Has a system for adding a tile datawatcher to tile entities, as well as an NBT based packet system for client->server
- Also has some WIP behavior tree stuff, not to be used

Some Details:
-------------

Usage:
------

- For custom mobs, c_IEnhPF Interface has a pathfind callback, use setPathExToEntity(...)
- Pathfind to entity: PFQueue.getPath(yourent, target, maxPFRange);
- Pathfind to location: PFQueue.getPath(this, x, y, z, maxPFRange);
- Pathfind to entity TOP PRIORITY: PFQueue.getPath(yourent, target, maxPFRange, -1);
- Pathfind to location TOP PRIORITY: PFQueue.getPath(this, x, y, z, maxPFRange, -1);

New methods:

In order to uphold total thread safety for even vanilla enhanced AI. Callbacks are now usable for processing a queue of returned paths on the main server thread, if parCallback is null it will resort to direct path injection
Methods:
- getPath(Entity var1, Entity var2, float var3, int priority, IPFCallback parCallback)
- getPath(Entity var1, int x, int y, int z, float var2, int priority, IPFCallback parCallback)

Extra entityless pathfind, for precalculating things without an entity
- getPath(ChunkCoordinatesSize coordSize, int x, int y, int z, float var2, int priority, IPFCallback parCallback)


- The priorities puts the pathfind call at the top of the queue list, even if there is a queue
- If range is bigger than maxPFRange, up to 5 ~20 block, slightly randomly angled ranged attempts are made aimed towards the destination, if those fail to find a presentable path, it tries a surface level pathfind as well.
- Pathfind calls that take over 5 seconds to get to in the queue are automatically aborted, prevents a buildup of the queue incase you overload it with jobs.