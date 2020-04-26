import Smart as smart


class DevRepr:
    def __init__(self, device):
        self.device = device
        self.commands = {"on": lambda: self.device.turnOn(),
                         "off": lambda: self.device.turnOff(),
                         'help': lambda: self.print_help()}

    def print_help(self):
        print("available commands:")
        print(list(self.commands.keys()))

    def execute_command(self, cmd):
        if len(cmd) == 2:
            self.commands.get(cmd[0], lambda x: print("Illegal command"))(cmd[1])
        elif len(cmd) == 1:
            self.commands.get(cmd[0], lambda: print("Illegal command"))()


class FridgeRepr(DevRepr):
    def __init__(self, smart_fridge):
        super().__init__(smart_fridge)
        self.commands.update({
            "add": lambda arg: self.add(arg),
            "contains": lambda arg: self.contains(arg),
            "remove": lambda arg: self.remove(arg)
        })

    def add(self, arg):
        self.device.addElement(arg)
        print("{} added".format(arg))

    def contains(self, arg):
        res = self.device.containsElement(arg)
        print("Fridge contains {}: {}".format(arg, res))

    def remove(self, arg):
        self.device.removeElement(arg)
        print(arg, "is removed from fridge")


class CoolerRepr(FridgeRepr):
    def __init__(self, device):
        super().__init__(device)
        self.commands.update({
            "setTemperature": lambda arg: self.set_temperature(arg),
            "getTemperature": lambda: self.print_temperature()
        })

    def set_temperature(self, arg):
        self.device.setTemperature(int(arg))
        print("Temperature set")

    def print_temperature(self):
        print("Current temperature {}".format(self.device.getTemperature()))


class DishwasherRepr(DevRepr):
    def __init__(self, device):
        super().__init__(device)
        self.commands.update({
            "start": lambda arg: self.start(arg),
            "isFree": lambda: self.isFree(),
            "unload": lambda: self.unload()
        })

    def start(self, arg):
        self.device.start(int(arg))
        print("Dishwasher started")

    def isFree(self):
        res = self.device.isFree()
        print("Dishwasher is free:", res)

    def unload(self):
        self.device.unload()
        print("Dishwasher unloaded")


class CameraRepr(DevRepr):
    def __init__(self, device):
        super().__init__(device)
        self.commands.update({
            "getPTZ": lambda: self.get_coords(),
            "setPTZ": lambda arg: self.set_PTZ(arg)
        })

    def print_help(self):
        print("available commands:")
        print(list(self.commands.keys()))
        print("setPTZ expects: 'h,r,zoom'")

    def get_coords(self):
        print(self.device.getPTZ())

    def set_PTZ(self, arg):
        split = arg.split(',')
        if len(split) != 3:
            print("Expected 'h,r,zoom'")
        coords = list(map(lambda x: int(x), split))
        print(coords)
        state = smart.CameraState(h=int(coords[0]), r=int(coords[1]), zoom=int(coords[2]))
        self.device.setPTZ(state)
        print("PTZ has been set")


class ArtCameraRepr(CameraRepr):
    def __init__(self, device):
        super().__init__(device)
        self.styles = {"CARTOON": 0, "DUOTONE": 1, "ASCII": 2, "POLAROID": 3}
        self.commands.update({
            "getStyle": lambda: self.get_style(),
            "setStyle": lambda arg: self.set_style(arg)
        })

    def print_help(self):
        super(ArtCameraRepr, self).print_help()
        print("available styles: \n", list(self.styles.keys()))

    def set_style(self, arg):
        if arg not in self.styles:
            print("illegal argument")
            return

        s = smart.style.valueOf(self.styles[arg])
        self.device.setStyle(s)
        print("Style set")

    def get_style(self):
        print("Current style:", self.device.getStyle())


class ThermalCameraRepr(CameraRepr):
    def __init__(self, device):
        super().__init__(device)
        self.commands.update({
            "setMinTemp": lambda arg: self.set_min_temp(arg),
            "setMaxTemp": lambda arg: self.set_max_temp(arg),
            "getMinTemp": lambda: self.get_min_temp(),
            "getMaxTemp": lambda: self.get_max_temp(),
        })

    def set_min_temp(self, arg):
        self.device.setMinTemp(float(arg))
        print("Temperature set")

    def set_max_temp(self, arg):
        self.device.setMaxTemp(float(arg))
        print("Temperature set")

    def get_min_temp(self):
        print("Min temp:", self.device.getMinTemp())

    def get_max_temp(self):
        print("Max temp:", self.device.getMaxTemp())
