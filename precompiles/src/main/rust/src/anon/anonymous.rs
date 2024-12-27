use super::{Deposit, OwnerShip, Transfer};
use crate::{Error, Result};
use jni::objects::{JByteArray, JClass};
use jni::sys::{jbyte, jlong};
use jni::JNIEnv;

#[no_mangle]
#[allow(unused_mut)]
pub extern "system" fn Java_org_hyperledger_besu_precompiles_Anonymous_nativeGas<'local>(
    mut env: JNIEnv<'local>,
    _class: JClass<'local>,
    input: JByteArray<'local>,
) -> jlong {
    let data = env.convert_byte_array(input).unwrap();

    gas(data.as_ref()).unwrap_or_default() as jlong
}

#[no_mangle]
#[allow(unused_mut)]
pub extern "system" fn Java_org_hyperledger_besu_precompiles_Anonymous_nativeRun<'local>(
    mut env: JNIEnv<'local>,
    _class: JClass<'local>,
    input: JByteArray<'local>,
) -> jbyte {
    let data = env.convert_byte_array(input).unwrap();

    if let Err(e) = verify(data.as_ref()) {
        e.code() as jbyte
    } else {
        0
    }
}

pub fn gas(data: &[u8]) -> Result<u64> {
    let args = Arguments::new(data)?;

    Ok(args.gas())
}

pub fn verify(data: &[u8]) -> Result<()> {
    let args = Arguments::new(data)?;
    args.check()?;

    Ok(())
}

pub enum Arguments {
    Deposit(Deposit),
    Transfer(Transfer),
    OwnerShip(OwnerShip),
}

// verify_deposit(bytes32[],bytes32[],uint128[],bytes[],bytes[],bytes32[]) 0x29efb148
pub const VERIFY_DEPOSIT_SELECTOR: [u8; 4] = [0x29, 0xef, 0xb1, 0x48];
// verify_transfer(bytes32,uint256,bytes32[],bytes32[],bytes32,bytes,bytes,bytes[],bytes32,uint256,uint256,bytes32) 0xd0b851ef
pub const VERIFY_TRANSFER_SELECTOR: [u8; 4] = [0xd0, 0xb8, 0x51, 0xef];
// verify_ownership(uint256,uint256,bytes,bytes,bytes,bytes,bytes32,bytes) 0x297db229
pub const VERIFY_WITHDRAW_SELECTOR: [u8; 4] = [0x29, 0x7d, 0xb2, 0x29];

impl Arguments {
    pub fn new(data: &[u8]) -> Result<Self> {
        if data.len() < 4 {
            return Err(Error::WrongSelectorLength);
        }

        match [data[0], data[1], data[2], data[3]] {
            VERIFY_DEPOSIT_SELECTOR => Ok(Self::Deposit(Deposit::new(&data[4..])?)),
            VERIFY_TRANSFER_SELECTOR => Ok(Self::Transfer(Transfer::new(&data[4..])?)),
            VERIFY_WITHDRAW_SELECTOR => Ok(Self::OwnerShip(OwnerShip::new(&data[4..])?)),
            _ => Err(Error::UnknownSelector),
        }
    }

    pub fn check(self) -> Result<()> {
        match self {
            Self::Deposit(v) => v.check(),
            Self::Transfer(v) => v.check(),
            Self::OwnerShip(v) => v.check(),
        }
    }

    pub fn gas(self) -> u64 {
        match self {
            Self::Deposit(v) => v.gas(),
            Self::Transfer(v) => v.gas(),
            Self::OwnerShip(v) => v.gas(),
        }
    }
}
