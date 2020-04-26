import Ice
import pyfiglet
import sys
# import gen.Smart as smart
from companion_objects import *

sep = "##################"


def handle_input(communicator, dev):
    print("{0} {1} {0}".format(sep, dev))
    print("Type 'help' for more information")
    print("Type 'exit' to exit this mode")
    try:
        address = "smart/{}:tcp -h localhost -p 10000".format(dev)
        obj = communicator.stringToProxy(address)
        communicate_device(obj, dev)
    except Exception as e:
        print(str(e))
        return


def start_handling(handler, dev_name):
    while True:
        command = input("({})#>".format(dev_name)).split()
        if len(command) == 0:
            continue
        if command[0] == "exit":
            break
        try:
            handler.execute_command(command)
        except Exception as e:
            print(str(e))


# todo: add to dictionary
def communicate_device(obj, dev_name):
    if dev_name.startswith("fridge"):
        handler = FridgeRepr(smart.SmartFridgePrx.checkedCast(obj))
        start_handling(handler, dev_name)
    elif dev_name.startswith("cooler"):
        handler = CoolerRepr(smart.SmartCoolerPrx.checkedCast(obj))
        start_handling(handler, dev_name)
    elif dev_name.startswith("dishwasher"):
        handler = DishwasherRepr(smart.SmartDishwasherPrx.checkedCast(obj))
        start_handling(handler, dev_name)
    elif dev_name.startswith("camera"):
        handler = CameraRepr(smart.SmartPTZCameraPrx.checkedCast(obj))
        start_handling(handler, dev_name)
    elif dev_name.startswith("artcamera"):
        handler = ArtCameraRepr(smart.SmartArtisticCameraPrx.checkedCast(obj))
        start_handling(handler, dev_name)
    elif dev_name.startswith("thermcamera"):
        handler = ThermalCameraRepr(smart.SmartThermalCameraPrx.checkedCast(obj))
        start_handling(handler, dev_name)


def get_available_devices():
    available_devices = []
    while True:
        try:
            obj = communicator.stringToProxy("provider/provider1:tcp -h localhost -p 10000")
            provider = smart.DevicesProviderPrx.checkedCast(obj)
            available_devices = provider.getDevices()
            return available_devices
        except Exception as e:
            print(str(e))


if __name__ == '__main__':
    ascii_banner = pyfiglet.figlet_format("Welcome to SmartHome!")
    print(ascii_banner)
    with Ice.initialize(sys.argv) as communicator:
        while True:
            available_devices = get_available_devices()
            print("{0} SELECT DEVICE {0}".format(sep))
            print("Available devices: ", ' '.join(available_devices))
            print("Type 'exit' to close client")
            line = input("#>")
            if line == "exit":
                break
            elif line in available_devices:
                handle_input(communicator, line)
            else:
                print("Illegal command")
